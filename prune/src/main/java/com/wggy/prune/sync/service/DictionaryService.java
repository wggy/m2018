package com.wggy.prune.sync.service;

import com.wggy.prune.sync.model.Dictionary;

/**
 * @author wggy
 * @create 2019-06-26 16:39
 **/
public interface DictionaryService {

    Dictionary findByKey(String key);

    int holdKey(String key);

    int releaseKey(String key);

    int updateValue(String key, String value);

    int updateValue(Dictionary dict);
}
