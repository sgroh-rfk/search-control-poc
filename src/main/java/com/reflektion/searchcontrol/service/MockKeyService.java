package com.reflektion.searchcontrol.service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.controller.NotFoundException;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyDTO;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.KeyValueDTO;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by apersson on 6/29/16.
 */
@Service
public class MockKeyService implements KeyService{

    private Map<Long,Key> keysByKeyId = Maps.newHashMap();
    private Map<Long,Map<Long, KeyValue>> keyValuesByKeyId = Maps.newHashMap();
    private static long counter = 1;

    @Override
    public Key getKeyByKeyId(Long keyId, Boolean includePermissions) {
        return keysByKeyId.get(keyId);
    }

    @Override
    public Set<Key> getKeys(Long parentKeyId, Boolean includePermissions, List<String> names) {
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
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive, List<String> domains) throws NotFoundException{
        Key key = getKeyByKeyId(keyId, false);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
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
    public Long createKey(KeyDTO key) throws Exception {
        if (keysByKeyId.containsKey(key.getId())) {
            throw new Exception("ERROR - KeyId already exists");
        }
        keysByKeyId.put(key.getId(), key.getEntityFromDTO());
        keyValuesByKeyId.put(key.getId(), Maps.newHashMap());
        return key.getId();
    }

    @Override
    public Key updateKey(Long keyId, KeyDTO key) throws NotFoundException {
        if (keyId==null)
            throw new NotFoundException(404, "Key not found, should not be null");
        Key keyToUpdate = getKeyByKeyId(keyId, false);
        if (keyToUpdate == null) {
            throw new NotFoundException(404, "Key not found");
        }
        Key updated = key.getEntityFromDTO();
        keysByKeyId.put(keyId, updated);
        return updated;
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
    public Long createKeyValueForKey(Long keyId, String userEmail, KeyValueDTO keyValue) throws NotFoundException {
        if (keyId == null)
            throw new NotFoundException(404, "Key id must not be null");
        Key key = getKeyByKeyId(keyId, false);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        if (!keysByKeyId.containsKey(keyId)) {
            throw new NotFoundException(404, "ERROR - KeyId does not exists");
        }
        keyValue.setKeyId(counter++);
        keyValuesByKeyId.get(keyId).put(keyValue.getKeyId(), keyValue.getEntityFromDTO(keyValue));
        return keyValue.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, Long keyValueId, KeyValueDTO keyValue, String userEmail) throws Exception {
        if (keyId == null) {
            throw new NotFoundException(404, "Key id should not be null");
        }
        if (keyValueId == null) {
            throw new NotFoundException(404, "Key value should not be null");
        }
        Key key = getKeyByKeyId(keyId, false);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        keyValue.setId(keyValueId);
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        KeyValue kv = keyValue.getEntityFromDTO(keyValue);
        keyValuesByKeyId.get(keyId).put(keyValue.getId(), kv);
        return kv;
    }

    @Override
    public Boolean deleteKeyValue(Long keyId, Long keyValueid) throws Exception {
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        keyValuesByKeyId.get(keyId).remove(keyValueid);
        return true;
    }

    @Override
    public KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws Exception {
        Key key = getKeyByKeyId(keyId, false);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        try {
            KeyValue keyValue = getKeyValuesForKeyIdAndKeyValueId(keyId, keyValueId);
        } catch(NotFoundException nfe){
            throw nfe;
        }
        if (!keysByKeyId.containsKey(keyId)) {
            throw new Exception("ERROR - KeyId does not exists");
        }
        return keyValuesByKeyId.get(keyId).get(keyValueId);
    }

}
