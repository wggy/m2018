package com.wggy.prune.sync.repository;

import com.wggy.prune.sync.model.ReportLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wggy
 * @create 2019-06-26 17:32
 **/
@Repository
public interface ReportLogRepository extends JpaRepository<ReportLog, Long> {

    @Query(value = "select * from tbl_report_log where status = ?1", nativeQuery = true)
    List<ReportLog> findListByStatus(Integer status);


    @Modifying
    @Query(value = "update tbl_report_log set current_id=?1 where id = ?2 and status=1", nativeQuery = true)
    List<ReportLog> updateCurrentId(Long currentId, Long id);

    Optional<ReportLog> findById(Long id);

    @Modifying
    @Query(value = "update tbl_report_log set status=?1, end_time=?2, cost_time=?3 where id = ?4 and status=1", nativeQuery = true)
    List<ReportLog> updateEnding(Integer status, Date endTime, Long costTime, Long id);
}
