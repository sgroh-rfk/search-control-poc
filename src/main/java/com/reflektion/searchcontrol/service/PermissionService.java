package com.reflektion.searchcontrol.service;

import com.google.common.collect.Sets;
import com.reflektion.searchcontrol.model.Key;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionDTO;
import com.reflektion.searchcontrol.model.PermissionKey;
import com.reflektion.searchcontrol.model.permission.Role;
import com.reflektion.searchcontrol.repository.KeyRepository;
import com.reflektion.searchcontrol.repository.PermissionKeyRepository;
import com.reflektion.searchcontrol.repository.PermissionRepository;
import com.reflektion.searchcontrol.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionKeyRepository permissionKeyRepository;
    KeyRepository keyRepository;
    UserRepository userRepository;

    @Inject
    public PermissionService(PermissionRepository permissionRepository,
                             PermissionKeyRepository permissionKeyRepository,
                             KeyRepository keyRepository,
                             UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.permissionKeyRepository = permissionKeyRepository;
        this.keyRepository = keyRepository;
        this.userRepository = userRepository;
    }

    public Permission findPermission(Long id){
        return permissionRepository.findOne(id);
    }

    /**
     * Get permission for all roles or for a specified set of them.
     * @param roleIds, a list of role ids.
     * @return all existent permissions or a set of permission based in the role specified.
     */
    public Set<Permission> getPermission(List<Long> roleIds) {
        if (null!=roleIds && roleIds.size() > 0 )
            return permissionRepository.findPermissionByroleIn(roleIds);
        return Sets.newHashSet(permissionRepository.findAll());
    }


    public Long addPermission(final PermissionDTO permission) {
        Permission p = new Permission(null, permission.getPermission());
        Permission newpermission = permissionRepository.save(p);
        return newpermission.getId();
    }

    public Set<Permission> getPermissionByKey(Long keyId) {
        return permissionRepository.findPermissionByKey(keyId);
    }

    @Transactional
    public Boolean addPermissionKey(Long keyId, Long userId) throws  IllegalArgumentException {
        Set<Permission> permissions = getUserPermissionBasedOnRoles(userId);
        Key k = keyRepository.findById(keyId);
        if (null==k)
            throw new IllegalArgumentException("Key should exist");
        PermissionKey pk;
        for(Permission p: permissions){
            pk = new PermissionKey();
            pk.setKey(k);
            pk.setPermission(p);
            pk = permissionKeyRepository.save(pk);
            if (null==pk.getId()) throw new IllegalArgumentException("PermissionKey could not be created");
        }
        return true;
    }

    @Transactional
    public Boolean deletePermissionKey(Long keyId, Long userId) throws  IllegalArgumentException {
        Set<PermissionKey> permissionKeys = permissionKeyRepository.findPermissionKeyByKeyId(keyId);
        permissionKeyRepository.delete(permissionKeys);
        return true;
    }

    private Set<Permission> getUserPermissionBasedOnRoles(final Long userId) throws IllegalArgumentException {
        Set<Role> roles = userRepository.findUserRoles(userId);
        Set<Permission> permissions = null;
        if (null!=roles && roles.size()>0){
            permissions = getPermission(roles.stream().map(role -> role.getId()).collect(Collectors.toList()));
        } else
            throw new IllegalArgumentException("The user must have roles assigned");
        return permissions;
    }

    @Transactional
    public Boolean updatePermissionKey(Long keyId, Long userId) {
        deletePermissionKey(keyId, userId);
        return addPermissionKey(keyId, userId);
    }
}
