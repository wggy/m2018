package com.wggy.prune.sync.service.impl;

import com.wggy.prune.sync.model.ReportHis;
import com.wggy.prune.sync.repository.ReportHisRepository;
import com.wggy.prune.sync.service.ReportHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wggy
 * @create 2019-06-26 16:39
 **/
@Service
public class ReportHisServiceImpl implements ReportHisService {
    @Autowired
    private ReportHisRepository reportHisRepository;
    @Override
    public Long findMaxId() {
        return reportHisRepository.findMaxId();
    }

    @Override
    public List<ReportHis> findListByStartIdEndId(Long startId, Long endId) {
        Specification<ReportHis> querySpeci = (Specification<ReportHis>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.greaterThan(root.get("recId").as(Long.class), startId));
            predicates.add(cb.lessThan(root.get("recId").as(Long.class), endId));
            return cb.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.ASC, "recId");

        PageRequest pageRequest = new PageRequest(0, 1000, sort);
        Page<ReportHis> pages = reportHisRepository.findAll(querySpeci, pageRequest);
        return pages.getContent();
    }
}
