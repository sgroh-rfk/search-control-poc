package com.reflektion.searchcontrol.service;

import com.google.common.base.Strings;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by apersson on 6/29/16.
 */
@Service
public class MockKeyService implements KeyService{

    private Hashtable<String,Key> keysByKeyName = new Hashtable<>();
    private Hashtable<String,Hashtable<Long, KeyValue>> keyValuesByKeyName = new Hashtable<>();

    @Override
    public Key getKeyByKeyName(String keyName) {
        return keysByKeyName.get(keyName);
    }

    @Override
    public List<Key> getKeys(String parentKeyName) {
        List<Key> values = new ArrayList<Key>();
        if (parentKeyName != null) {
            for (Key key : new ArrayList<Key>(keysByKeyName.values())) {
                if (null!=key.getParentKey() && null!= key.getParentKey().getName() && key.getParentKey().getName().equals(parentKeyName)) {
                    values.add(key);
                }
            }
        } else {
            values = new ArrayList<Key>(keysByKeyName.values());
        }
        return values;
    }

    @Override
    public List<KeyValue> getKeyValuesForKey(String keyName, Boolean isLive) {
        List<KeyValue> values = new ArrayList<KeyValue>();
        if (isLive != null) {
            for (KeyValue value : new ArrayList<KeyValue>(keyValuesByKeyName.get(keyName).values())) {
                if (value.getLive().equals(isLive)) {
                    values.add(value);
                }
            }
        } else {
            values = new ArrayList<KeyValue>(keyValuesByKeyName.get(keyName).values());
        }
        return values;
    }

    @Override
    public String createKey(String keyName, Key key) throws Exception {
        if (keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName already exists");
        }
        keysByKeyName.put(keyName, key);
        keyValuesByKeyName.put(keyName, new Hashtable<>());
        return keyName;
    }

    @Override
    public Key updateKey(String keyName, Key key) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keysByKeyName.put(keyName, key);
        return key;
    }

    @Override
    public void deleteKey(String keyName, Key key) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keysByKeyName.remove(keyName);
        keyValuesByKeyName.remove(keyName);
    }

    @Override
    public Long createKeyValueForKey(String keyName, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyName).put(keyValue.getId(), keyValue);
        return keyValue.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(String keyName, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyName).put(keyValue.getId(), keyValue);
        return keyValue;
    }

    @Override
    public void deleteKeyValue(String keyName, KeyValue keyValue) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        keyValuesByKeyName.get(keyName).remove(keyValue.getId());
    }

    @Override
    public KeyValue getKeyValuesForKeyNameAndKeyValueId(String keyName, String keyValueId) throws Exception {
        if (!keysByKeyName.containsKey(keyName)) {
            throw new Exception("ERROR - KeyName does not exists");
        }
        return keyValuesByKeyName.get(keyName).get(keyValueId);
    }

}
