package com.reflektion.searchcontrol.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.controller.NotFoundException;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.KeyDTO;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.KeyValueDTO;
import com.reflektion.searchcontrol.model.permission.User;
import com.reflektion.searchcontrol.repository.KeyRepository;
import com.reflektion.searchcontrol.repository.KeyValueRepository;
import com.reflektion.searchcontrol.repository.UserRepository;
import javassist.bytecode.annotation.BooleanMemberValue;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class KeyServiceImpl implements KeyService {

    KeyRepository keyRepository;
    UserRepository userRepository;
    KeyValueRepository keyValueRepository;
    UserService userService;

    @Inject
    public KeyServiceImpl(KeyRepository keyRepository,
                          UserRepository userRepository,
                          KeyValueRepository keyValueRepository,
                          UserService userService){
        this.keyRepository = keyRepository;
        this.userRepository = userRepository;
        this.keyValueRepository = keyValueRepository;
        this.userService = userService;
    }

    @Override
    public Key getKeyByKeyId(Long keyId, Boolean includePermissions) {
        return getKeyByKeyIdPermissions(keyId, includePermissions);
    }

    private Key getKeyByKeyId(Long keyId) {
        return getKeyByKeyIdPermissions(keyId, false);
    }

    private Key getKeyByKeyIdPermissions(Long keyId, Boolean includePermissions) {
        Key k = keyRepository.findById(keyId);
        if (Boolean.FALSE.equals(includePermissions)) {
            k.setPermissions(null);
            if (k.getParentKey() != null){
                k.getParentKey().setPermissions(null);
                k.getParentKey().setKeyValues(null);
            }
        } else {
            if (k.getPermissions()!=null)
                k.getPermissions().stream().forEach(pk -> {
                    if (!pk.getKey().getId().equals(keyId)){
                        pk.setPermission(null);
                    }
                    else if (pk.getPermission()!=null) {
                        pk.getPermission().setRoles(null);
                    }
                    pk.setKey(null);
                });

                if (k.getParentKey()!= null) {
                    if (k.getParentKey().getPermissions()!=null)
                        k.getParentKey().getPermissions().stream().forEach(ppk -> {
                            ppk.setKey(null);
                            if (ppk.getPermission()!=null) {
                                ppk.getPermission().setKeys(null);
                                ppk.getPermission().setRoles(null);
                                ppk.setKey(null);
                            }
                        });
                    k.getParentKey().setKeyValues(null);
                }

        }
        k.setKeyValues(null);
        return k;
    }

    @Override
    public Set<Key> getKeys(final Long parentId, Boolean includePermissions,  List<String> names) {
        Set<Key> keys;
        if (null!=parentId && parentId.longValue()>=0 && names!=null && names.size()>0) {
            keys = Sets.newHashSet(keyRepository.findByParentKeyAndNames(parentId, names));
        } else if (parentId!=null && parentId.longValue()>=0) {
            keys = Sets.newHashSet(keyRepository.findByParentKey(parentId));
        } else if (names!=null && names.size()>=0) {
            keys = Sets.newHashSet(keyRepository.findOnlyByNames(names));
        }
        else {
            keys = Sets.newHashSet(keyRepository.findAll());
        }
        keys.stream()
                .forEach(k -> {
                    k.setKeyValues(null);
                    if (k.getPermissions()!=null)
                        k.getPermissions().stream().forEach(p ->  p.setKey(null));
                    if (Boolean.FALSE.equals(includePermissions))
                        k.setPermissions(null);
                    //Parent permissions
                    if(k.getParentKey()!=null) {
                        k.getParentKey().setKeyValues(null);
                        if (Boolean.FALSE.equals(includePermissions)) {
                            k.getParentKey().setPermissions(null);
                        } else {
                            if (k.getParentKey().getPermissions()!=null)
                                k.getParentKey().getPermissions().stream().forEach(pk -> {
                                    pk.setKey(null);
                                    if (pk.getPermission()!=null) {
                                        pk.getPermission().setKeys(null);
                                        pk.setKey(null);
                                    }
                                });
                        }
                    }
                });
        return keys;
    }

    @Override
    public Set<KeyValue> getKeyValuesForKey(Long keyId, Boolean isLive) throws NotFoundException{
        //TODO: filter by not deleted attribute
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
    public Long createKeyValueForKey(Long keyId, String userEmail, KeyValueDTO keyValue) throws NotFoundException {
        if (keyId == null)
            throw new NotFoundException(404, "Key id must not be null");
        User user;
        if (Strings.isNullOrEmpty(userEmail)){
            throw new NotFoundException(404, "User id must not be null");
        } else {
            user = userRepository.findByEmail(userEmail);
            if (user==null){
                throw new NotFoundException(404, "User does not exists");
            }
        }
        Key key = getKeyByKeyId(keyId);
        if (key == null) {
            throw new NotFoundException(404, "Key not found");
        }
        KeyValue kv = new KeyValue();
        kv = kv.updateWithDTO(keyValue);
        kv.setKey(key);
        kv.setUser(user);
        kv.setId(null);
        kv.setDeleted(false);
        kv = keyValueRepository.save(kv);
        return kv.getId();
    }

    @Override
    public KeyValue updateKeyValueForKey(Long keyId, Long keyValueId, KeyValueDTO keyValue, String userEmail) throws NotFoundException {
        User user;
        if (Strings.isNullOrEmpty(userEmail)){
            throw new NotFoundException(404, "user email should not be null");
        }  else {
            user = userRepository.findByEmail(userEmail);
            if (user==null){
             throw new NotFoundException(404, "User does not exists");
            }
        }
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
        kv.setUser(user);
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
        // keyValueRepository.delete(kv);
        kv.setDeleted(true); //Performs a logical delete
        keyValueRepository.save(kv);
        return kv.getId()!=0;
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
            throw new NotFoundException(404, "KeyValue not found");
        }
        if (keyValue.getKey()!=null)
            keyValue.getKey().setKeyValues(null);
        return keyValue;
    }
}
