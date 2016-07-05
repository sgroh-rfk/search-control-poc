package com.reflektion.searchcontrol.service;

import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by apersson on 6/29/16.
 */
public interface KeyService {

    public Key getKeyByKeyId(Long keyId);

    public Set<Key> getKeys(Long parentId);

    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive);

    public Long createKey(Long keyId, Key key) throws Exception;

    public Key updateKey(Long keyId, Key key) throws Exception;

    public void deleteKey(Long keyId, Key key) throws Exception;

    public Long createKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception;

    public KeyValue updateKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception;

    public void deleteKeyValue(Long keyId, KeyValue keyValue) throws Exception;

    KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception;
}
