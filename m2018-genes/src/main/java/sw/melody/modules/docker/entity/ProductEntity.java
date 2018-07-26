package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/***
 * Created by ping on 2018-7-26
 */
@Setter
@Getter
public class ProductEntity {
    private Long id;
    private String productCode;
    private String productName;
    private String genes;
    private Date createTime;
}
