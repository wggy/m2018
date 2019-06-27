package com.wggy.prune.sync.service.impl;

import com.wggy.prune.sync.model.ToReport;
import com.wggy.prune.sync.repository.ToReportRepository;
import com.wggy.prune.sync.service.ToReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wggy
 * @create 2019-06-27 15:45
 **/
@Service
public class ToReportServiceImpl implements ToReportService {
    @Autowired
    private ToReportRepository toReportRepository;
    @Override
    public void save(ToReport report) {
        toReportRepository.save(report);
    }

    @Override
    public void saveAll(List<ToReport> list) {
        toReportRepository.saveAll(list);
    }
}
