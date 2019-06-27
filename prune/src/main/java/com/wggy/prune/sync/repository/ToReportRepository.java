package com.wggy.prune.sync.repository;

import com.wggy.prune.sync.model.ToReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wggy
 * @create 2019-06-27 15:33
 **/
@Repository
public interface ToReportRepository extends JpaRepository<ToReport, Long> {
}
