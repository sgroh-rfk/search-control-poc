package com.reflektion.searchcontrol.service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by apersson on 6/29/16.
 */
@Service
public class MockKeyService implements KeyService{

    private Map<Long,Key> keysByKeyName = Maps.newHashMap();
    private Map<Long,Map<Long, KeyValue>> keyValuesByKeyName = Maps.newHashMap();

    @Override
    public Key getKeyByKeyName(Long keyName) {
        return keysByKeyName.get(keyName);
    }

    @Override
    public Set<Key> getKeys(String parentKeyName) {
        Set<Key> values = Sets.newHashSet();
        if (parentKeyName != null) {
            for (Key key : new ArrayList<Key>(keysByKeyName.values())) {
                if (null!=key.getParentKey() && null!= key.getParentKey().getName() && key.getParentKey().getName().equals(parentKeyName)) {
                    values.add(key);
                }
            }
        } else {
            values = Sets.newHashSet(keysByKeyName.values());
        }
        return values;
    }

    @Override
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive) {
        Set<KeyValue> values = Sets.newHashSet();
        if (isLive != null) {
            for (KeyValue value : new ArrayList<KeyValue>(keyValuesByKeyName.get(keyId).values())) {
                if (value.getLive().equals(isLive)) {
                    values.add(value);
                }
            }
        } else {
            values = Sets.newHashSet(keyValuesByKeyName.get(keyId).values());
        }
        return values;
    }

    @Override
    public Long createKey(Long keyId, Key key) throws Exception {
        if (keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName already exists");
        }
        keysByKeyName.put(keyId, key);
        keyValuesByKeyName.put(keyId, Maps.newHashMap());
        return keyId;
    }

    @Override
    public Key updateKey(Long keyId, Key key) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keysByKeyName.put(keyId, key);
        return key;
    }

    @Override
    public void deleteKey(Long keyId, Key key) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keysByKeyName.remove(keyId);
        keyValuesByKeyName.remove(keyId);
    }

    @Override
    public Long createKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyId).put(keyValue.getId(), keyValue);
        return keyValue.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyId).put(keyValue.getId(), keyValue);
        return keyValue;
    }

    @Override
    public void deleteKeyValue(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyId).remove(keyValue.getId());
    }

    @Override
    public KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception {
        if (!keysByKeyName.containsKey(keyId)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        return keyValuesByKeyName.get(keyId).get(keyValueId);
    }

}
