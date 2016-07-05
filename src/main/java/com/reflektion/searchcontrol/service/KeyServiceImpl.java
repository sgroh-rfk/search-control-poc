package com.reflektion.searchcontrol.service;

import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.repository.KeyRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class KeyServiceImpl implements KeyService {

    KeyRepository keyRepository;

    @Inject
    public KeyServiceImpl(KeyRepository keyRepository){
        this.keyRepository = keyRepository;
    }

    @Override
    public Key getKeyByKeyId(Long keyId) {
        return keyRepository.findById(keyId);
    }

    @Override
    public Set<Key> getKeys(Long parentId) {

        return null;
    }

    @Override
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive) {
        return null;
    }

    @Override
    public Long createKey(Long keyId, Key key) throws Exception {
        return null;
    }

    @Override
    public Key updateKey(Long keyId, Key key) throws Exception {
        return null;
    }

    @Override
    public void deleteKey(Long keyId, Key key) throws Exception {

    }

    @Override
    public Long createKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        return null;
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        return null;
    }

    @Override
    public void deleteKeyValue(Long keyId, KeyValue keyValue) throws Exception {

    }

    @Override
    public KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception {
        return null;
    }
}
