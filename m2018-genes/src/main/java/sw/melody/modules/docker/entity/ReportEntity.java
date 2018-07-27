package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/***
 * Created by ping on 2018-7-26
 */
@Setter
@Getter
public class ReportEntity implements Serializable {
    // 报告编号，即病患编号
    private String sickCode;
    // 检验产品
    private String product;
    // 病患姓名
    private String sickName;
    // 报告时间
    private String reportTime;
    // 报告状态
    private String reportStatus;
}
