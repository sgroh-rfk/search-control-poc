package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.permission.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface KeyValueRepository extends CrudRepository<KeyValue, Long> {

    KeyValue findById(Long id);

    @Query("select kv from key_value kv where kv.key.id = ?1 and kv.live = ?2")
    Set<KeyValue> findAllByKeyId(Long keyId, Boolean isLive);

    @Query("select kv from key_value kv where kv.key.id = ?1")
    Set<KeyValue> findAllByKeyId(Long keyId);

}
