package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ping
 * @create 2018-08-09 10:47
 **/
@Setter
@Getter
public class SickRelationEntity {

    // 报告编号

    private String reportCode;

    // 患者姓名

    private String sickName;

    // 与先证者关系

    private String relation = "先证者";

    // 疾病状态

    private boolean isDisease = true;

    // 测序质量

    private boolean seqQuality;

    // 测序深度

    private boolean seqDepth;

    // 产品

    private String productName;

    // 报告类型

    private String reportType = "临床报告";

    // 携带筛查

    private boolean isCarry = false;

    // 送检医生

    private String docker;


}
