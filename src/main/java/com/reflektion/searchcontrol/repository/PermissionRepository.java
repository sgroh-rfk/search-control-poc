package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    Permission findById(Long id);

    @Query("select p from permission p left join p.roles role where role.id in ?1")
    Set<Permission> findPermissionByroleIn(List<Long> roleIds);

    //TODO: this should be HQL but it's mapping it to an incorrect join
    @Query(value = "select * from permission p left outer join permission_key pk on p.id=pk.permission_id where pk.key_id=?1", nativeQuery = true)
    Set<Permission> findPermissionByKey(Long keyId);
}
