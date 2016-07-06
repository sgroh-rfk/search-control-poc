package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface KeyRepository extends CrudRepository<Key, Long> {

    Key findById(Long id);

    @Query("select k from keytable k where k.parentKey.id = ?1")
    Set<Key> findByParentKey(Long parentKeyId);


}
