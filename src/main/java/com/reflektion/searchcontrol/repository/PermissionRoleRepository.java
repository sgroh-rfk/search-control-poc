package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.PermissionKey;
import com.reflektion.searchcontrol.model.PermissionRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface PermissionRoleRepository extends CrudRepository<PermissionRole, Long> {

    PermissionRole findById(Long id);

    @Query("select pr from permission_role pr where pr.role.id = ?1 and pr.permission.id = ?2")
    PermissionRole findByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
