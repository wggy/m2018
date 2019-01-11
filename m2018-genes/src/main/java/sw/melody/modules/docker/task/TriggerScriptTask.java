package sw.melody.modules.docker.task;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import sw.melody.common.utils.ConfigConstant;
import sw.melody.common.utils.Constant;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.sys.entity.SysConfigEntity;
import sw.melody.modules.sys.service.SysConfigService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ping
 * @create 2019-01-10 15:12
 **/
@Component
public class TriggerScriptTask extends Thread implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(TriggerScriptTask.class);
    static final String triggerScriptSampleId = "TRIGGER_SCRIPT_SAMPLE_ID";
    private static SampleService sampleService;
    private static SysConfigService sysConfigService;
    static final int showStatus = 1;
    static final int hideStatus = 0;
    private static final String success = Constant.SampleStatus.Success.getStatus();

    private static final LinkedBlockingDeque<Long> queue = new LinkedBlockingDeque<>();
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition notEmpty = lock.newCondition();
    private static final int waitSeconds = 60;
    private static final int releaseWaitSeconds = 30;

    private static class ReleaseThread extends Thread {
        ReleaseThread() {
            this.setName("Release-lock-thread");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Long sampleId = queue.take();
                    if (sampleId != null) {
                        SysConfigEntity config = sysConfigService.getObjectByKey(triggerScriptSampleId);
                        if (config == null) {
                            log.error("【{}】互斥锁未配置", triggerScriptSampleId);
                            pushReq(sampleId);
                        } else {
                            if (config.getStatus() == showStatus) {
                                log.info("样本：【{}】执行中", config.getValue());
                                pushReq(sampleId);
                                log.info("最新样本：【{}】加入失败", sampleId);
                            } else if (config.getStatus() == hideStatus) {
                                if (sysConfigService.updateKeyLock(triggerScriptSampleId) > 0) {
                                    log.info("线程：【{}】获取锁成功", this.getName());
                                    sysConfigService.updateValueByKey(triggerScriptSampleId, sampleId.toString());
                                    if (sysConfigService.updateKeyUnLock(triggerScriptSampleId) > 0) {
                                        log.info("线程：【{}】释放锁成功", this.getName());
                                    } else {
                                        log.info("线程：【{}】释放锁失败", this.getName());
                                    }
                                } else {
                                    log.info("线程：【{}】获取锁失败", this.getName());
                                    pushReq(sampleId);
                                }
                            } else {
                                log.error("互斥锁错误的状态：【{}】", config.getStatus());
                                pushReq(sampleId);
                            }
                        }
                        log.info("线程>>>>>>>>>>>>>【】睡眠30秒<<<<<<<<<<<<<<<<", this.getName());
                        Thread.sleep(releaseWaitSeconds * 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            SysConfigEntity config = sysConfigService.getObjectByKey(triggerScriptSampleId);
            if (config == null) {
                log.error("【{}】互斥锁未配置", triggerScriptSampleId);
            } else {
                if (config.getStatus() == showStatus) {
                    log.info("样本：【{}】 运行中...", config.getValue());
                } else if (config.getStatus() == hideStatus) {
                    if (sysConfigService.updateKeyLock(triggerScriptSampleId) > 0) {
                        // 成功获取锁
                        config = sysConfigService.getObjectByKey(triggerScriptSampleId);
                        SampleEntity sampleEntity = sampleService.queryObject(Long.parseLong(config.getValue()));
                        if (sampleEntity == null) {
                            log.error("样本：【{}】查无记录", config.getValue());
                            sysConfigService.updateKeyUnLock(triggerScriptSampleId);
                        } else if (success.equals(sampleEntity.getTriggerStatus())) {
                            log.info("最后一个样本：>>>>>>>>>【{}】已经调度完成<<<<<<<<<<<<", sampleEntity.getId());
                            sysConfigService.updateKeyUnLock(triggerScriptSampleId);
                        } else {
                            if (triggerScript(sampleEntity)) {
                                log.info("样本：【{}】调用成功", config.getValue());
                                PollLogFileTask.pushReq(sampleEntity.getSickId());
                            } else {
                                log.info("脚本调用失败，样本：【{}】", config.getValue());
                                sysConfigService.updateKeyUnLock(triggerScriptSampleId);
                            }
                        }
                    } else {
                        log.info("线程：【{}】获取锁失败", this.getName());
                    }
                } else {
                    log.error("互斥锁错误的状态：【{}】", config.getStatus());
                }
            }
            log.info("线程>>>>>>>>>>>>>【】等待60秒<<<<<<<<<<<<<<<<", this.getName());
            waitMinute();
        }
    }


    private void waitMinute() {
        try {
            lock.lockInterruptibly();
            long nanos = TimeUnit.SECONDS.toNanos(waitSeconds);
            while (nanos > 0) {
                nanos = notEmpty.awaitNanos(nanos);
            }
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void pushReq(Long sampleId) {
        queue.add(sampleId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setName("trigger-script-task");
        this.start();
        new ReleaseThread().start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sampleService = applicationContext.getBean(SampleService.class);
        sysConfigService = applicationContext.getBean(SysConfigService.class);
    }

    static boolean triggerScript(SampleEntity entity) {
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
            Process cpFilePs;
            try {
                cpFilePs = Runtime.getRuntime().exec("cp -pf " + bashFilePath + " " + fullPathNoFile);
                int cpFileStatus = cpFilePs.waitFor();
                if (cpFileStatus != 0) {
                    log.error("中间文件拷贝失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
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
