package sw.melody.modules.docker.task;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import sw.melody.common.utils.Constant;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;

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

                        TriggerScriptTask.pushReq(frtSample.getId());
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
    }

    public static void pushReq(Long sickId) {
        queue.add(sickId);
    }

}
