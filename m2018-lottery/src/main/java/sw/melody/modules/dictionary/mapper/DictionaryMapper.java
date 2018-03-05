package sw.melody.modules.dictionary.mapper;

import org.apache.ibatis.annotations.*;
import sw.melody.modules.dictionary.entity.DictionaryEntity;

/***
 * Created by ping on 2018/3/1
 */
@Mapper
public interface DictionaryMapper {
    @Insert("INSERT INTO sw_dictionary(dkey, dvalue) VALUES(#{dkey}, #{dvalue})")
    void save(@Param("dkey") String dkey, @Param("dvalue") String dvalue);

    @Update("update sw_dictionary set dkey=#{dkey} where dvalue=#{dvalue})")
    void update(@Param("dkey") String dkey, @Param("dvalue") String dvalue);

    @Select("select * from sw_dictionary where dkey=#{dkey}")
    DictionaryEntity queryByDkey(String dkey);
}
