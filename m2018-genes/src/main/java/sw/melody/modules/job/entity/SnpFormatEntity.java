package sw.melody.modules.job.entity;

import lombok.Getter;
import lombok.Setter;

/***
 * Created by ping on 2018/6/22
 * @author wange
 */
@Setter
@Getter
public class SnpFormatEntity {

    private Integer id;
    private Integer snpId;
    private String sick;
    private String format;
    private String formatVal;
    private String formatRate;
    private String mutationMode;
    private String mutationAd;
    private String mutationRate;
}
