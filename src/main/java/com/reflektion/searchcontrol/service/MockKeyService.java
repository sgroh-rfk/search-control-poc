package com.reflektion.searchcontrol.service;

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

    private Map<Long,Key> keysByKeyId = Maps.newHashMap();
    private Map<Long,Map<Long, KeyValue>> keyValuesByKeyId = Maps.newHashMap();

    @Override
    public Key getKeyByKeyId(Long keyId) {
        return keysByKeyId.get(keyId);
    }

    @Override
    public Set<Key> getKeys(Long parentKeyId) {
        Set<Key> values = Sets.newHashSet();
        if (parentKeyId != null) {
            for (Key key : new ArrayList<Key>(keysByKeyId.values())) {
                if (null!=key.getParentKey() && null!= key.getParentKey().getId() && key.getParentKey().getId().equals(parentKeyId)) {
                    values.add(key);
                }
            }
        } else {
            values = Sets.newHashSet(keysByKeyId.values());
        }
        return values;
    }

    @Override
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive) {
        Set<KeyValue> values = Sets.newHashSet();
        if (isLive != null) {
            for (KeyValue value : new ArrayList<KeyValue>(keyValuesByKeyId.get(keyId).values())) {
                if (value.getLive().equals(isLive)) {
                    values.add(value);
                }
            }
        } else {
            values = Sets.newHashSet(keyValuesByKeyId.get(keyId).values());
        }
        return values;
    }

    @Override
    public Long createKey(Long keyId, Key key) throws Exception {
        if (keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId already exists");
        }
        keysByKeyId.put(keyId, key);
        keyValuesByKeyId.put(keyId, Maps.newHashMap());
        return keyId;
    }

    @Override
    public Key updateKey(Long keyId, Key key) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keysByKeyId.put(keyId, key);
        return key;
    }

    @Override
    public void deleteKey(Long keyId, Key key) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keysByKeyId.remove(keyId);
        keyValuesByKeyId.remove(keyId);
    }

    @Override
    public Long createKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keyValuesByKeyId.get(keyId).put(keyValue.getId(), keyValue);
        return keyValue.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keyValuesByKeyId.get(keyId).put(keyValue.getId(), keyValue);
        return keyValue;
    }

    @Override
    public void deleteKeyValue(Long keyId, KeyValue keyValue) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keyValuesByKeyId.get(keyId).remove(keyValue.getId());
    }

    @Override
    public KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        return keyValuesByKeyId.get(keyId).get(keyValueId);
    }

}
