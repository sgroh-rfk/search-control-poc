package com.reflektion.searchcontrol.service;

import com.google.common.collect.Lists;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionDTO;
import com.reflektion.searchcontrol.model.PermissionRole;
import com.reflektion.searchcontrol.model.permission.User;
import com.reflektion.searchcontrol.repository.PermissionRepository;
import com.reflektion.searchcontrol.repository.PermissionRoleRepository;
import com.reflektion.searchcontrol.repository.RoleRepository;
import com.reflektion.searchcontrol.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class UserService {

    UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserId(final String email) {
        User u = userRepository.findByEmail(email);
        return u.getId();
    }

}
