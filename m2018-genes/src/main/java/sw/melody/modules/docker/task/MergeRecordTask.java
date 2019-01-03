package sw.melody.modules.docker.task;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ping
 * @create 2018-12-29 17:15
 **/
@Component
public class MergeRecordTask extends Thread implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MergeRecordTask.class);
    private static final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();
    private static final String success = Constant.SampleStatus.Success.getStatus();
    private static final String running = Constant.SampleStatus.Running.getStatus();

    private static SampleService sampleService;
    private static SysConfigService sysConfigService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setName("merge-record-task");
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Long sickId = queue.take();
                if (sickId != null) {
                    List<SampleEntity> list = sampleService.queryListSickId(sickId);
                    if (CollectionUtils.isEmpty(list) || list.size() != 2) {
                        log.info(new Gson().toJson(list));
                        log.error("病人：{} 的样本为空或者不是一对的", sickId);
                        continue;
                    }
                    SampleEntity frtSample = list.get(0);
                    SampleEntity secSample = list.get(1);
                    if (success.equals(frtSample.getUploadStatus()) && success.equals(secSample.getUploadStatus())) {
                        log.info("样本上传成功，frtSample: {}, secSample: {}", frtSample.getId(), secSample.getId());
                        frtSample.setSecOriginName(secSample.getOriginName());
                        sampleService.update(frtSample);
                        sampleService.deleteByFlag(secSample.getId());
                        log.info("样本记录合并完成，frtSample: {}, secSample: {}，开始调度执行样本", frtSample.getId(), secSample.getId());

                        if (triggerScript(frtSample)) {
                            PollLogFileTask.pushReq(sickId);
                            frtSample.setTriggerStartTime(new Date());
                            frtSample.setTriggerStatus(running);
                            sampleService.update(frtSample);
                            log.info("加入轮询日志请求队列成功，sickId； {}", sickId);
                        } else {
                            log.error("调用解析脚本失败");
                        }

                    } else {
                        log.error("样本未上传成功，frtSample: {} - 上传状态: {}, secSample: {} - 上传状态: {}",
                                frtSample.getId(), frtSample.getUploadStatus(), secSample.getId(), secSample.getUploadStatus());
                        continue;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sampleService = applicationContext.getBean(SampleService.class);
        sysConfigService = applicationContext.getBean(SysConfigService.class);
    }

    public static void pushReq(Long sickId) {
        queue.add(sickId);
    }

    static boolean triggerScript(SampleEntity entity) throws IOException, InterruptedException {
        String targetFileName = entity.getOriginName();
        String bashFilePath = sysConfigService.getValue(ConfigConstant.SAMPLE_SHELL_PATH);
        File bashFile = new File(bashFilePath);
        if (!bashFile.exists()) {
            log.error("bash文件不存在");
            return false;
        }

        File fullPathFile = new File(SaveFile.linkFileSeparator(entity.getLocation()) + entity.getOriginName());
        if (!fullPathFile.exists()) {
            log.error("样本1不存在");
            return false;
        }

        String secFileName = "";
        if (!StringUtils.isBlank(entity.getSecOriginName())) {
            File secFullPathFile = new File(SaveFile.linkFileSeparator(entity.getLocation()) + entity.getSecOriginName());
            if (!secFullPathFile.exists()) {
                log.error("样本2不存在");
            }
            secFileName = entity.getSecOriginName();
        }

        String fullPathNoFile = entity.getLocation();
        String shellFileName = sysConfigService.getValue(ConfigConstant.Shell_Bwa_File);
        File targetBashFile = new File(SaveFile.linkFileSeparator(fullPathNoFile) + shellFileName);
        if (!targetBashFile.exists()) {
            Process cpFilePs = Runtime.getRuntime().exec("cp -pf " + bashFilePath + " " + fullPathNoFile);
            int cpFileStatus = cpFilePs.waitFor();
            if (cpFileStatus != 0) {
                log.error("中间文件拷贝失败");
            }
        }

        String command = SaveFile.callShellCommand(fullPathNoFile, targetFileName, secFileName);
        log.info("command: {}", command);

        triggerShell(entity, command);
        return true;
    }

    static void triggerShell(SampleEntity entity, String command) {
        Process ps;
        try {
            ps = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            int status = ps.waitFor();
            log.info("status: {}", status);
            if (status != 0) {
                log.error("Failed to call shell's command ");
                entity.setTriggerStatus(Constant.SampleStatus.Fail.getStatus());
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    log.info(line);
                }
                entity.setTriggerStatus(Constant.SampleStatus.Running.getStatus());
                br.close();
            }
        } catch (Exception e) {
            entity.setTriggerStatus(Constant.SampleStatus.Fail.getStatus());
            e.printStackTrace();
        }
        entity.setTriggerFinishTime(new Date());
        sampleService.update(entity);
    }
}
