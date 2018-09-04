package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/***
 * 样本实体
 * @author wange
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

    private Date uploadStartTime;
    private String uploadStatus;
    private Date uploadFinishTime;

    //触发脚本时间

    private Date triggerStartTime;
    private String triggerStatus;
    private Date triggerFinishTime;

    //入库时间

    private Date storeStartTime;
    private String storeStatus;
    private Date storeFinishTime;

    private String sickName;
    private String sickCode;
    private String md5;


    private Integer deleteFlag = 1;
    private String secOriginName;

}
