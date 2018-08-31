package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/***
 * Created by ping on 2018-7-26
 */
@Setter
@Getter
public class ReportEntity implements Serializable {
    // 病人信息

    private String sickCode;
    private String sickName;
    private Long sickId;

    // 检验产品

    private String productName;

    // 样本信息

    private Long sampleId;
    private Date storeStartTime;
    private Date storeFinishTime;
    private String storeStatus;
}
