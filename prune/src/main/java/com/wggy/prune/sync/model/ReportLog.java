package com.wggy.prune.sync.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wggy
 * @create 2019-06-26 17:25
 **/
@Data
@Entity
@Table(name = "tbl_report_log")
public class ReportLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workName;
    private Long threadId;
    private Long startId;
    private Long endId;
    private Long currentId;
    private Date startTime;
    private Date endTime;
    private Long costTime;
    private Integer status;
    private Long batchId;
}
