package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface PermissionKeyRepository extends CrudRepository<PermissionKey, Long> {

    List<PermissionKey> findById(Long id);

    @Query("select pk from permission_key pk where pk.key.id = ?1")
    Set<PermissionKey> findPermissionKeyByKeyId(Long keyId);
}
