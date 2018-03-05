package sw.melody.modules.dictionary.entity;

import lombok.Data;

/***
 * Created by ping on 2018/3/1
 */
@Data
public class DictionaryEntity {
    private Integer id;
    private String dkey;
    private String dvalue;
    private String dversion;
}
