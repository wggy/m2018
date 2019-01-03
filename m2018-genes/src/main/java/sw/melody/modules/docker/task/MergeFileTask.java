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
import sw.melody.modules.docker.entity.SickEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.service.SickService;
import sw.melody.modules.docker.util.MergeFile;
import sw.melody.modules.sys.service.SysConfigService;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ping
 * @create 2018-12-28 18:23
 **/
@Component
public class MergeFileTask extends Thread implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(MergeFileTask.class);
    private static final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    private static SampleService sampleService;
    private static SickService sickService;
    private static SysConfigService sysConfigService;

    public static void pushReq(Long sampleId) {
        queue.add(sampleId);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Long sampleId = queue.take();
                if (sampleId != null) {
                    SampleEntity entity = sampleService.queryObject(sampleId);
                    if (entity != null) {
                        String guid = entity.getOriginName().substring(0, entity.getOriginName().lastIndexOf("."));
                        String ext = entity.getOriginName().substring(entity.getOriginName().lastIndexOf("."));
                        SickEntity sick = sickService.queryObject(entity.getSickId());
                        if (sick == null) {
                            log.error("样本：{} 未关联到病人", sampleId);
                        }
                        String uploadFolderPath = sysConfigService.getValue(ConfigConstant.UPLOAD_FILE_PREFIX);
                        String fullPathNoFile = ConfigConstant.getFullPathNoFile(uploadFolderPath, sick.getSickCode(), guid);
                        String location = ConfigConstant.getFullPath(uploadFolderPath, sick.getSickCode());



                        log.info("{}: 文件‘{}’开始合并", this.getName(), entity.getOriginName());
                        entity.setUploadStatus(Constant.SampleStatus.Merging.getStatus());
                        sampleService.update(entity);
                        MergeFile.mergeFileByNio(entity.getChunksNumber(), ext, guid, fullPathNoFile);
                        entity.setUploadFinishTime(new Date());
                        entity.setUploadStatus(Constant.SampleStatus.Success.getStatus());
                        entity.setLocation(location);
                        sampleService.update(entity);
                        log.info("{}: 文件‘{}’合并文件完成", this.getName(), entity.getOriginName());
                        MergeRecordTask.pushReq(entity.getSickId());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sampleService = applicationContext.getBean(SampleService.class);
        sickService = applicationContext.getBean(SickService.class);
        sysConfigService = applicationContext.getBean(SysConfigService.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setName("merge-file-thread");
        this.start();
    }
}
