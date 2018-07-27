package sw.melody.modules.docker.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/***
 * Created by ping on 2018-7-27
 */
@Setter
@Getter
public class SickProductEntity implements Serializable {
    private Long id;
    private Long sickId;
    private Long productId;
}
