package com.wggy.prune.sync;

import com.wggy.prune.sync.model.Dictionary;
import com.wggy.prune.sync.model.ReportHis;
import com.wggy.prune.sync.model.ReportLog;
import com.wggy.prune.sync.model.ToReport;
import com.wggy.prune.sync.service.DictionaryService;
import com.wggy.prune.sync.service.ReportHisService;
import com.wggy.prune.sync.service.ReportLogService;
import com.wggy.prune.sync.service.ToReportService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author wggy
 * @create 2019-06-26 16:22
 **/
@Slf4j
public class BigTableSyncer {
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private ReportLogService reportLogService;
    @Autowired
    private ReportHisService reportHisService;
    @Autowired
    private ToReportService toReportService;
    private volatile boolean isStop = false;
    private static final String ReportCurrentIdConfig = "REPORT_CURRENT_ID";
    private static final String ReportMaxIdConfig = "REPORT_MAX_ID";
    private static final String ReportStepNumConfig = "REPORT_STEP_NUM";
    private static final String ReportThreadNumConfig = "REPORT_THREAD_NUM";
    private static final String SUCCESS = "SUCCESS";
    private static long ReportCurrentId = 0L;
    private static long ReportMaxId = 0L;
    private static int ReportStepNum = 10000;
    private static int ReportThreadNum = 10;
    private static int ReportThreadIndex = 1;
    private static int RunningStatus = 1;
    private static int FinishStatus = 0;
    private CountDownLatch countDownLatch;
    private volatile Boolean isRepair = false;
    private volatile long batchId;
    private static final LinkedBlockingDeque<WorkThread> TriggerQueue = new LinkedBlockingDeque();

    public String execute(String... params) throws Exception {
        long start = System.currentTimeMillis();
        long end;
        if (this.repair()) {
            log.info("修复数据中...");
            while (!this.isStop) {
                Boolean ret = this.countDownLatch.await(10L, TimeUnit.SECONDS);
                if (ret.booleanValue()) {
                    this.isStop = true;
                    end = System.currentTimeMillis();
                    log.info("修复完成，合计耗时：{}", end - start);
                }
            }
            return SUCCESS;
        }
        this.initial();
        while (!this.isStop) {
            WorkThread workThread = (WorkThread) TriggerQueue.poll(10L, TimeUnit.SECONDS);
            if (workThread != null && this.assign()) {
                this.isStop = true;
                end = System.currentTimeMillis();
                log.info("同步完成，合计耗时：{}", end - start);
            }
        }
        return SUCCESS;
    }

    public void initial() {
        ReportStepNum = Integer.parseInt(this.dictionaryService.findByKey(ReportStepNumConfig).getDictValue());
        ReportThreadNum = Integer.parseInt(this.dictionaryService.findByKey(ReportThreadNumConfig).getDictValue());
        log.info("处理器个数：{} ", Runtime.getRuntime().availableProcessors());
        Long maxId = this.reportHisService.findMaxId();
        Dictionary reportMaxIdDict = this.dictionaryService.findByKey(ReportMaxIdConfig);
        log.info("激活最大rec_id: {}, 上次同步rec_id: {}, 步长: {}", maxId, reportMaxIdDict.getDictValue(), maxId - Long.parseLong(reportMaxIdDict.getDictValue()));
        reportMaxIdDict.setDictValue(maxId.toString());
        this.dictionaryService.updateValue(reportMaxIdDict);
        ReportMaxId = maxId;
        this.batchId = System.currentTimeMillis() / 1000L;
        ReportThreadIndex = 1;
        this.isStop = false;
        this.isRepair = false;

        while (ReportThreadIndex <= ReportThreadNum && !this.assign()) {

        }

    }

    boolean repair() {
        List<ReportLog> list = this.reportLogService.findListByStatus(RunningStatus);
        if (CollectionUtils.isEmpty(list)) {
            log.info("无需修复中断数据...s");
            return false;
        }
        this.isRepair = true;
        this.countDownLatch = new CountDownLatch(list.size());
        for (ReportLog reportLog : list) {
            WorkThread workThread = new WorkThread(reportLog, System.currentTimeMillis());
            workThread.start();
        }
        return true;
    }


    private boolean assign() {
        boolean ret = false;
        Dictionary reportCurrDict = this.dictionaryService.findByKey(ReportCurrentIdConfig);
        ReportCurrentId = Long.parseLong(reportCurrDict.getDictValue());
        if (ReportCurrentId < ReportMaxId) {
            long end = ReportCurrentId + ReportStepNum;
            if (end > ReportMaxId) {
                end = ReportMaxId;
                ret = true;
            }

            log.info("分配自增线程ID： {}, 报号ID： {}", ReportThreadIndex, ReportCurrentId);
            WorkThread workThread = new WorkThread(ReportThreadIndex, ReportCurrentId, end, System.currentTimeMillis());
            workThread.start();
            ReportCurrentId = end;
            ReportThreadIndex++;
            reportCurrDict.setDictValue(end + "");
            this.dictionaryService.updateValue(reportCurrDict);
        } else {
            ret = true;
        }

        return ret;
    }

    @Setter
    @Getter
    private class WorkThread extends Thread {
        private String jobName;
        private long startId;
        private long endId;
        private long startTime;
        private long jobId;

        public WorkThread(ReportLog reportLog, long startTime) {
            this.jobName = reportLog.getWorkName();
            this.startId = reportLog.getCurrentId().intValue();
            this.endId = reportLog.getEndId().intValue();
            this.jobId = reportLog.getId().intValue();
            this.startTime = startTime;
            this.setName(this.jobName);
        }

        public WorkThread(long threadId, long startId, long endId, long startTime) {
            this.jobName = "thread-" + threadId + "-" + (startId + 1) + "-" + endId;
            this.startId = startId;
            this.endId = endId;
            this.startTime = startTime;
            this.setName(this.jobName);
            ReportLog reportLog = new ReportLog();
            reportLog.setWorkName(this.jobName);
            reportLog.setStartId(startId);
            reportLog.setEndId(endId);
            reportLog.setThreadId(threadId);
            reportLog.setCurrentId(startId);
            reportLog.setStartTime(new Timestamp(startTime));
            reportLog.setStatus(RunningStatus);
            reportLog.setBatchId(batchId);
            this.jobId = reportLogService.save(reportLog).getId();
        }

        @Override
        public void run() {
            long recId = this.startId;

            while (true) {
                List<ReportHis> list;
                try {
                    list = reportHisService.findListByStartIdEndId(recId, this.endId);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                if (CollectionUtils.isEmpty(list)) {
                    log.info("recId : {}, 查无数据，同步完成。", recId);
                    break;
                }

                // save to other DB
                List<ToReport> newList = new ArrayList<>();
                for (ReportHis his : list) {
                    newList.add(ToReport.toReport(his));
                }
                toReportService.saveAll(newList);
                recId = list.get(list.size() - 1).getRecId();
                reportLogService.updateCurrentId(jobId, recId);
                log.info("同步recId: {}", recId);
            }

            long end = System.currentTimeMillis();
            ReportLog reportLog = reportLogService.findById(jobId);
            reportLog.setStatus(FinishStatus);
            reportLog.setEndTime(new Timestamp(end));
            reportLog.setCostTime(end - reportLog.getStartTime().getTime());
            reportLogService.updateEnding(reportLog);
            log.info("线程：{}任务执行完毕！", jobName);
            if (isRepair) {
                countDownLatch.countDown();
            } else {
                TriggerQueue.add(this);
            }
        }
    }
}
