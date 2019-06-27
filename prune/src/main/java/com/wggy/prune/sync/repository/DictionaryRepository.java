package com.wggy.prune.sync.repository;

import com.wggy.prune.sync.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author wggy
 * @create 2019-06-26 17:40
 **/
@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    @Query(value = "SELECT * FROM tbl_dictionary WHERE dict_key = ?1 and status = 1 limit 1", nativeQuery = true)
    Dictionary findByDictKey(String dictKey);

    @Modifying
    @Query(value = "update tbl_dictionary set status = 1 where dict_key = ?1 and status = 0", nativeQuery = true)
    int holdDictKey(String dickKey);

    @Modifying
    @Query(value = "update tbl_dictionary set status = 0 where dict_key = ?1 and status = 1", nativeQuery = true)
    int releaseDictKey(String dickKey);

    @Modifying
    @Query(value = "update tbl_dictionary set dict_value = ?1, last_update_time=CURRENT_TIMESTAMP() where dict_key = ?2 and status = 1", nativeQuery = true)
    int updateValue(String dictValue, String dictKey);

}
