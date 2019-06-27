package com.wggy.prune.sync.service;

import com.wggy.prune.sync.model.ToReport;

import java.util.List;

/**
 * @author wggy
 * @create 2019-06-27 15:45
 **/
public interface ToReportService {

    void save(ToReport report);

    void saveAll(List<ToReport> list);
}
