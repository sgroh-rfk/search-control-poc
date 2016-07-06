package com.reflektion.searchcontrol.service;

import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.controller.NotFoundException;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyDTO;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.KeyValueDTO;
import com.reflektion.searchcontrol.repository.KeyRepository;
import com.reflektion.searchcontrol.repository.KeyValueRepository;
import com.reflektion.searchcontrol.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class KeyServiceImpl implements KeyService {

    KeyRepository keyRepository;
    UserRepository userRepository;
    KeyValueRepository keyValueRepository;

    @Inject
    public KeyServiceImpl(KeyRepository keyRepository,
                          UserRepository userRepository,
                          KeyValueRepository keyValueRepository){
        this.keyRepository = keyRepository;
        this.userRepository = userRepository;
        this.keyValueRepository = keyValueRepository;
    }

    @Override
    public Key getKeyByKeyId(Long keyId) {
        return keyRepository.findById(keyId);
    }

    @Override
    public Set<Key> getKeys(final Long parentId) {
        if (null!=parentId)
            return Sets.newHashSet(keyRepository.findByParentKey(parentId));
        return Sets.newHashSet(keyRepository.findAll());
    }

    @Override
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive) throws NotFoundException{
        Key key = getKeyByKeyId(keyId);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        Set<KeyValue> keyValues;
        if (isLive==null)
            keyValues = Sets.newHashSet(keyValueRepository.findAllByKeyId(keyId));
        else
            keyValues = Sets.newHashSet(keyValueRepository.findAllByKeyId(keyId, isLive));
        keyValues.stream()
                .filter(kv -> kv.getKey()!=null)
                .forEach(kv -> kv.getKey().setKeyValues(null));
        return keyValues;
    }

    @Override
    public Long createKey(KeyDTO key) throws Exception {
        Key k = key.getEntityFromDTO();
        k.setId(null);
        k =  keyRepository.save(k);
        return k.getId();
    }

    @Override
    public Key updateKey(Long keyId, KeyDTO key) throws NotFoundException {
        if (keyId==null)
            throw new NotFoundException(404, "Key not found, should not be null");
        Key keyToUpdate = getKeyByKeyId(keyId);
        keyToUpdate.setId(keyId);
        if (keyToUpdate == null) {
            throw new NotFoundException(404, "Key not found");
        }
        keyToUpdate = keyToUpdate.updateWithDTO(key);
        if(key.getParentId()!= null){
            keyToUpdate.setParentKey(getKeyByKeyId(key.getParentId()));
        }
        return keyRepository.save(keyToUpdate);
    }

    @Override
    public void deleteKey(Long keyId, Key key) throws Exception {

    }

    @Override
    public Long createKeyValueForKey(Long keyId, Long userId, KeyValueDTO keyValue) throws NotFoundException {
        if (keyId == null)
            throw new NotFoundException(404, "Key id must not be null");

        if (userId == null)
            throw new NotFoundException(404, "User id must not be null");
        Key key = getKeyByKeyId(keyId);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        KeyValue kv = new KeyValue();
        kv = kv.updateWithDTO(keyValue);
        kv.setKey(key);
        kv.setUser(userRepository.findById(userId));
        kv.setId(null);
        kv = keyValueRepository.save(kv);
        return kv.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, Long keyValueId, KeyValueDTO keyValue) throws NotFoundException {
        if (keyId == null) {
            throw new NotFoundException(404, "Key id should not be null");
        }
        if (keyValueId == null) {
            throw new NotFoundException(404, "Key value should not be null");
        }
        Key key = getKeyByKeyId(keyId);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        keyValue.setId(keyValueId);
        keyValue.setKeyId(keyId);
        KeyValue kv = keyValueRepository.findById(keyValueId);
        if (kv == null) {
            throw new NotFoundException(404, "KeyValue not found");
        }
        kv = kv.updateWithDTO(keyValue);
        kv = keyValueRepository.save(kv);
        if (null!=kv && kv.getKey()!=null)
            kv.getKey().setKeyValues(null);
        return kv;
    }

    @Override
    public Boolean deleteKeyValue(Long keyId, Long keyValueId) throws NotFoundException {
        if (keyId == null) {
            throw new NotFoundException(404, "Key id should not be null");
        }
        if (keyValueId == null) {
            throw new NotFoundException(404, "Key value id should not be null");
        }
        KeyValue kv = getKeyValuesForKeyIdAndKeyValueId(keyId, keyValueId);
        keyValueRepository.delete(kv);
        return true;
    }

    @Override
    public KeyValue getKeyValuesForKeyIdAndKeyValueId(Long keyId, Long keyValueId) throws NotFoundException {
        Key key = getKeyByKeyId(keyId);
        if (key == null) {
            throw new NotFoundException(404, "Key not found, could not be null");
        }
        if (keyValueId == null) {
            throw new NotFoundException(404, "Key value id should not be null");
        }
        KeyValue keyValue = keyValueRepository.findById(keyValueId);
        if (keyValue == null) {
            throw new NotFoundException(404, "KeyValue not found, could not be null");
        }
        if (keyValue.getKey()!=null)
            keyValue.getKey().setKeyValues(null);
        return keyValue;
    }
}
