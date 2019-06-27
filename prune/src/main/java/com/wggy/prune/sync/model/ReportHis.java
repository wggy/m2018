package com.wggy.prune.sync.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author wggy
 * @create 2019-06-26 17:51
 **/
@Data
@Entity
@Table(name = "tbl_phone_report_his")
public class ReportHis {

    private Long recId;
    private String imei;
    private String imei2;
    private String location;
    private String meid;
    private String nerwork;
    private String networkOperator;
    private String networkOperator2;
    private String phoneModel;
    private Timestamp reportTime;
    private String romVersion;
    private String softVersion;
}
