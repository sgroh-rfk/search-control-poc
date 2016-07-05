package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionRole;
import com.reflektion.searchcontrol.model.permission.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findById(Long id);

}
