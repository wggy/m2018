package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SickEntity implements Serializable {
    private Long id;
    // 病人编码，新增时自动生成，格式：HY+时间戳
    private String sickCode;
    // 病人姓名
    @NotBlank(message="参数名不能为空")
    private String sickName;
    // 性别
    @NotBlank(message="参数值不能为空")
    private String sex;
    // 家庭成员
    private String family;
    // 疾病基因
    private String diseaseGeneFocused;
    // 病史药史
    private String medicalHistory;
    // 家族病史
    private String familyHistory;
    // 病情描述
    private String panelName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    private List<Long> productIdList;
}
