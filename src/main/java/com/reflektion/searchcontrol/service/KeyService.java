package com.reflektion.searchcontrol.service;

import com.reflektion.searchcontrol.controller.NotFoundException;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyDTO;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.KeyValueDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by apersson on 6/29/16.
 */
public interface KeyService {

    public Key getKeyByKeyId(Long keyId, Boolean includePermissions);

    public Set<Key> getKeys(Long parentId, Boolean includePermissions, List<String> names);

    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive, List<String> domains) throws NotFoundException;

    public Long createKey(KeyDTO key) throws Exception;

    public Key updateKey(Long keyId, KeyDTO key) throws NotFoundException;

    public void deleteKey(Long keyId, Key key) throws Exception;

    public Long createKeyValueForKey(Long keyId, String userEmail, KeyValueDTO keyValue) throws NotFoundException;

    public KeyValue updateKeyValueForKey(Long keyId, Long keyValueId, KeyValueDTO keyValue, String userEmail) throws Exception;

    public Boolean deleteKeyValue(Long keyId, Long keyValueId) throws Exception;

    KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception;
}
