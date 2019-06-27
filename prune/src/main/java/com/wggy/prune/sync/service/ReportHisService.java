package com.wggy.prune.sync.service;

import com.wggy.prune.sync.model.ReportHis;

import java.util.List;

/**
 * @author wggy
 * @create 2019-06-26 16:38
 **/
public interface ReportHisService {
    Long findMaxId();

    List<ReportHis> findListByStartIdEndId(Long startId, Long endId);

}
