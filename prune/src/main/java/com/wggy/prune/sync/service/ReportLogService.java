package com.wggy.prune.sync.service;

import com.wggy.prune.sync.model.ReportLog;

import java.util.List;

/**
 * @author wggy
 * @create 2019-06-26 16:59
 **/
public interface ReportLogService {

    List<ReportLog> findListByStatus(Integer status);

    ReportLog save(ReportLog reportLog);

    void updateCurrentId(long id, long currentId);

    ReportLog findById(Long id);

    void updateEnding(ReportLog reportLog);
}
