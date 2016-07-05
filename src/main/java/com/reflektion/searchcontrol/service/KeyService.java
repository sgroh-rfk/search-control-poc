package com.reflektion.searchcontrol.service;

import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apersson on 6/29/16.
 */
public interface KeyService {

    public Key getKeyByKeyName(String keyName);

    public List<Key> getKeys(String parentKeyName);

    public List<KeyValue> getKeyValuesForKey(String keyName, Boolean isLive);

    public String createKey(String keyName, Key key) throws Exception;

    public Key updateKey(String keyName, Key key) throws Exception;

    public void deleteKey(String keyName, Key key) throws Exception;

    public Long createKeyValueForKey(String keyName, KeyValue keyValue) throws Exception;

    public KeyValue updateKeyValueForKey(String keyName, KeyValue keyValue) throws Exception;

    public void deleteKeyValue(String keyName, KeyValue keyValue) throws Exception;

    KeyValue getKeyValuesForKeyNameAndKeyValueId(String keyName, String keyValueId) throws Exception;
}
