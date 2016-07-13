package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.KeyValue;
import com.reflektion.searchcontrol.model.permission.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface KeyValueRepository extends CrudRepository<KeyValue, Long> {
    @Query("select kv from key_value kv where kv.id = ?1 and kv.deleted=0")
    KeyValue findById(Long id);

    @Query("select kv from key_value kv where kv.key.id = ?1 and kv.live = ?2 and kv.deleted=0")
    Set<KeyValue> findAllByKeyId(Long keyId, Boolean isLive);

    @Query("select kv from key_value kv where kv.key.id = ?1 and kv.live = ?2 and kv.domain in ?3 and kv.deleted=0")
    Set<KeyValue> findAllByKeyIdIsLiveAndDomains(Long keyId, Boolean isLive, List<String> domains);

    @Query("select kv from key_value kv where kv.key.id = ?1 and kv.domain in ?2 and kv.deleted=0")
    Set<KeyValue> findAllByKeyAndDomains(Long keyId, List<String> domains);

    @Query("select kv from key_value kv where kv.key.id = ?1 and kv.deleted=0")
    Set<KeyValue> findAllByKeyId(Long keyId);

}
