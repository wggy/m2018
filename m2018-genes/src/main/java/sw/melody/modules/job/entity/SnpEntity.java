package sw.melody.modules.job.entity;

import lombok.Getter;
import lombok.Setter;
import sw.melody.common.exception.RRException;

/***
 * Created by ping on 2018/6/21
 * @author wange
 */
@Setter
@Getter
public class SnpEntity {
    private Integer id;
    private String chrom;
    private String pos;
    private String posId;
    private String ref;
    private String alt;
    private String qual;
    private String filter;
    private String info;
    private Integer createTime;
    private String dataType;
    private Long sickId;

    public SnpEntity() {
    }

    public SnpEntity(String[] cols, String dataType, Long sickId) {
        if (cols == null || cols.length < 9) {
            throw new RRException("数据行不匹配");
        }
        this.chrom = cols[0];
        this.pos = cols[1];
        this.posId = cols[2];
        this.ref = cols[3];
        this.alt = cols[4];
        this.qual = cols[5];
        this.filter = cols[6];
        this.info = cols[7];
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.dataType = dataType;
        this.sickId = sickId;
    }
}
