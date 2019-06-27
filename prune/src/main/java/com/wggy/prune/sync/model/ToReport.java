package com.wggy.prune.sync.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author wggy
 * @create 2019-06-27 15:37
 **/
@Data
@Entity
@Table(name = "tbl_report")
public class ToReport {

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

    public static ToReport toReport(ReportHis reportHis) {
        ToReport report = new ToReport();
        report.setRecId(reportHis.getRecId());
        report.setImei2(reportHis.getImei2());
        report.setLocation(reportHis.getLocation());
        report.setMeid(reportHis.getMeid());
        report.setNerwork(reportHis.getNerwork());
        report.setNetworkOperator(reportHis.getNetworkOperator());
        report.setNetworkOperator2(reportHis.getNetworkOperator2());
        report.setPhoneModel(reportHis.getPhoneModel());
        report.setReportTime(reportHis.getReportTime());
        report.setRomVersion(reportHis.getRomVersion());
        report.setSoftVersion(reportHis.getSoftVersion());
        return report;
    }

}
