package com.wggy.prune.sync.repository;

import com.wggy.prune.sync.model.ReportHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * @author wggy
 * @create 2019-06-26 18:11
 **/
@Repository
public interface ReportHisRepository extends JpaRepository<ReportHis, Long>, JpaSpecificationExecutor<ReportHis> {
    @Query(value = "select max(rec_id) from tbl_phone_report_his", nativeQuery = true)
    Long findMaxId();

    @Query(value = "from ReportHis where recId > ?1 and recId < ?2 order by recId asc")
    List<ReportHis> findListByStartIdEndId(Long startId, Long endId, Pageable pageable);
}
