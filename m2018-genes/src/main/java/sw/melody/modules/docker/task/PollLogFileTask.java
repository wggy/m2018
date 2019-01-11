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
import sw.melody.common.utils.Constant.SampleStatus;
import sw.melody.modules.docker.entity.SampleEntity;
import sw.melody.modules.docker.service.SampleService;
import sw.melody.modules.docker.util.MoreLogUtil;
import sw.melody.modules.docker.util.SaveFile;
import sw.melody.modules.sys.service.SysConfigService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ping
 * @create 2018-12-29 8:57
 **/
@Component
public class PollLogFileTask extends Thread implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(PollLogFileTask.class);
    private static final LinkedBlockingQueue<Long> triggerDeque = new LinkedBlockingQueue <>();
    private static final String success = SampleStatus.Success.getStatus();
    private static final String running = SampleStatus.Running.getStatus();
    private static final String successMsg = "##########所有程序执行完成";
    private static final ReentrantLock takeLock = new ReentrantLock();
    private static final Condition notEmpty = takeLock.newCondition();
    private static final int waitSeconds = 60;
    private static SampleService sampleService;
    private static SysConfigService sysConfigService;
    @Override
    public void run() {
        while (true) {
            try {
                Long sickId = triggerDeque.take();
                if (sickId != null) {
                    List<SampleEntity> entityList = sampleService.queryListSickId(sickId);
                    if (CollectionUtils.isEmpty(entityList)) {
                        log.error("病人：{} 的样本记录为null", sickId);
                        break;
                    }
                    SampleEntity entity = entityList.get(0);
                    if (success.equals(entity.getUploadStatus())
                            && StringUtils.isNotBlank(entity.getTriggerStatus())
                            && running.equals(entity.getTriggerStatus())) {

                        String logFileName = SaveFile.linkFileSeparator(entity.getLocation()).concat(entity.getOriginName()).concat(".out");
                        String logResult = MoreLogUtil.getLastLine(logFileName);
                        log.info("最后一行数据：{}", logResult);

                        if (StringUtils.isNotBlank(logResult) && (logResult.startsWith(successMsg) || logResult.contains(success))) {
                            entity.setTriggerFinishTime(new Date());
                            entity.setTriggerStatus(success);
                            sampleService.update(entity);
                            MoreLogUtil.closeRaf(logFileName);
                            StoreResultTask.pushReq(entity.getId());
                            log.info("病人：{} 的样本解析完成", sickId);
                            if (sysConfigService.updateKeyUnLock(TriggerScriptTask.triggerScriptSampleId) > 0) {
                                log.info("样本：【{}】解析完成，释放锁成功", entity.getId());
                            } else {
                                log.info("样本：【{}】解析完成，释放锁失败", entity.getId());
                            }
                        } else {
                            takeLock.lockInterruptibly();
                            try {
                                long nanos = TimeUnit.SECONDS.toNanos(waitSeconds);
                                while (nanos > 0) {
                                    nanos = notEmpty.awaitNanos(nanos);
                                }
                                notEmpty.signal();
                            } finally {
                                takeLock.unlock();
                            }
                            pushReq(sickId);
                        }
                    } else {
                        log.error("病人记录不符合监控条件, json = {}", new Gson().toJson(entity));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public static void pushReq(Long sickId) {
        if (triggerDeque.contains(sickId)) {
            log.error("该病人样本已加入请求队列中：sickId = {}", sickId);
            return;
        }
        triggerDeque.add(sickId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sampleService = applicationContext.getBean(SampleService.class);
        sysConfigService = applicationContext.getBean(SysConfigService.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setName("poll-log-task");
        this.start();
    }
}
