package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/***
 * 样本实体
 */
@Setter
@Getter
public class SampleEntity implements Serializable {
    private Long id;
    // 病人编码，新增时自动生成，格式：HY+时间戳
    private Long sickId;
    private String originName;
    // 病人姓名
    @NotBlank(message="参数名不能为空")
    private String location;
    //上传时间
    private Date uploadTime;
    // 病情描述
    private String handlerStatus;
    //更新时间
    private Date handlerTime;
    //触发时间
    private Date triggerTime;
    private String sickName;
    private String sickCode;
    private Date storeTime;
    private String storeStatus;
    private Date finishTime;
}
