package com.wggy.prune.sync.service.impl;

import com.wggy.prune.sync.model.ReportLog;
import com.wggy.prune.sync.repository.ReportLogRepository;
import com.wggy.prune.sync.service.ReportLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wggy
 * @create 2019-06-26 16:59
 **/
@Service
public class ReportLogServiceImpl implements ReportLogService {

    @Autowired
    private ReportLogRepository reportLogRepository;

    @Override
    public List<ReportLog> findListByStatus(Integer status) {
        return reportLogRepository.findListByStatus(status);
    }

    @Override
    public ReportLog save(ReportLog reportLog) {
        return reportLogRepository.save(reportLog);
    }

    @Override
    public void updateCurrentId(long id, long currentId) {
        reportLogRepository.updateCurrentId(currentId, id);
    }

    @Override
    public ReportLog findById(Long id) {
        return reportLogRepository.findById(id).get();
    }

    @Override
    public void updateEnding(ReportLog reportLog) {
        reportLogRepository.updateEnding(reportLog.getStatus(), reportLog.getEndTime(), reportLog.getCostTime(), reportLog.getId());
    }
}
