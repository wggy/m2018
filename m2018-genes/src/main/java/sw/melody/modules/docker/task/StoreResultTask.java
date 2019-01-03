package sw.melody.modules.docker.task;

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
import sw.melody.modules.job.task.GeneIndelTask;
import sw.melody.modules.job.task.GeneSnpTask;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author ping
 * @create 2019-01-02 15:00
 **/
@Component
public class StoreResultTask extends Thread implements ApplicationContextAware, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(StoreResultTask.class);
    private static final String success = Constant.SampleStatus.Success.getStatus();
    private static final LinkedBlockingDeque<Long> queue = new LinkedBlockingDeque<>();
    private static SampleService sampleService;
    private static GeneIndelTask geneIndelTask;
    private static GeneSnpTask geneSnpTask;

    @Override
    public void run() {
        try {
            while (true) {
                Long sampleId = queue.take();
                if (sampleId != null) {
                    SampleEntity sampleEntity = sampleService.queryObject(sampleId);
                    if (sampleEntity == null) {
                        log.error("查无上传记录");
                        continue;
                    }
                    if (!success.equals(sampleEntity.getTriggerStatus())) {
                        log.error("样本未成功解析，无法入库...");
                        continue;
                    }
                    sampleEntity.setStoreStatus(Constant.SampleStatus.Running.getStatus());
                    sampleEntity.setStoreStartTime(new Date());
                    sampleService.update(sampleEntity);

                    String location = sampleEntity.getLocation();
                    String fileName = sampleEntity.getOriginName().substring(0, sampleEntity.getOriginName().indexOf("."));
                    String indelPath = SaveFile.linkFileSeparator(location).concat(fileName).concat(ConfigConstant.Result_Indel_File_Prefix);
                    String snpPath = SaveFile.linkFileSeparator(location).concat(fileName).concat(ConfigConstant.Result_Snp_File_Prefix);
                    Long sickId = sampleEntity.getSickId();
                    try {
                        log.info("indel store: {}", indelPath);
                        geneIndelTask.parse(indelPath, sickId);
                        log.info("snp store: {}", snpPath);
                        geneSnpTask.parse(snpPath, sickId);
                        sampleEntity.setStoreStatus(success);
                        sampleEntity.setStoreFinishTime(new Date());
                        sampleService.update(sampleEntity);
                        log.info("sampleId: {} 入库完成", sampleId);
                    } catch (Exception e) {
                        sampleEntity.setStoreStatus(Constant.SampleStatus.Fail.getStatus());
                        sampleEntity.setStoreFinishTime(new Date());
                        sampleService.update(sampleEntity);
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void pushReq(Long sampleId) {
        queue.add(sampleId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setName("merge-result-task");
        this.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sampleService = applicationContext.getBean(SampleService.class);
        geneIndelTask = applicationContext.getBean(GeneIndelTask.class);
        geneSnpTask = applicationContext.getBean(GeneSnpTask.class);
    }
}
