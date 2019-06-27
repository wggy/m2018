package com.wggy.prune.sync.service.impl;

import com.wggy.prune.sync.model.Dictionary;
import com.wggy.prune.sync.repository.DictionaryRepository;
import com.wggy.prune.sync.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wggy
 * @create 2019-06-26 16:39
 **/
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    public Dictionary findByKey(String key) {
        return dictionaryRepository.findByDictKey(key);
    }

    @Override
    public int holdKey(String key) {
        return dictionaryRepository.holdDictKey(key);
    }

    @Override
    public int releaseKey(String key) {
        return dictionaryRepository.releaseDictKey(key);
    }

    @Override
    public int updateValue(String key, String value) {
        return dictionaryRepository.updateValue(key, value);
    }

    @Override
    public int updateValue(Dictionary dict) {
        return dictionaryRepository.updateValue(dict.getDictValue(), dict.getDictValue());
    }
}
