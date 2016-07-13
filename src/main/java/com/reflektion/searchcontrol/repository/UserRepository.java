package com.reflektion.searchcontrol.repository;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.permission.Role;
import com.reflektion.searchcontrol.model.permission.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);

    User findByEmail(String email);

    @Query("select u.roles from user u left join u.roles where u.id = ?1")
    Set<Role> findUserRoles(Long userId);

}
