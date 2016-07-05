package com.reflektion.searchcontrol.service;

import com.google.common.collect.Lists;
import com.reflektion.searchcontrol.model.Permission;
import com.reflektion.searchcontrol.model.PermissionDTO;
import com.reflektion.searchcontrol.model.PermissionRole;
import com.reflektion.searchcontrol.model.permission.Role;
import com.reflektion.searchcontrol.repository.PermissionRepository;
import com.reflektion.searchcontrol.repository.PermissionRoleRepository;
import com.reflektion.searchcontrol.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Set;

/**
 * @author sebastiangroh@reflektion.com
 */
@Service
public class RoleService {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    PermissionRoleRepository permissionRoleRepository;

    @Inject
    public RoleService(PermissionRepository userRepository,
                       RoleRepository roleRepository,
                       PermissionRoleRepository permissionRoleRepository) {
        this.permissionRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRoleRepository = permissionRoleRepository;
    }

    public Set<Permission> getPermissionByRole(Long roleId){
        return permissionRepository.findPermissionByroleIn(Lists.newArrayList(roleId));
    }

    @Transactional
    public Long addPermissiontoRole(final Long roleId, final Long permissionId) {
        PermissionRole pr = new PermissionRole();
        pr.setPermission(permissionRepository.findById(permissionId));
        pr.setRole(roleRepository.findById(roleId));
        pr.setId(null);
        PermissionRole newPermissionRole = permissionRoleRepository.save(pr);
        return newPermissionRole.getId();
    }

    @Transactional
    public Boolean updatePermissiontoRole(PermissionDTO permission, Long roleId) {
        PermissionRole pr = getPermissionRole(permission.getId(), roleId);
        Permission p = permissionRepository.findById(permission.getId());
        p.setPermission(permission.getPermission());
        permissionRepository.save(p);
        return true;
    }

    @Transactional
    public Boolean deletePermissionRole(Long permissionId, Long roleId) {
        PermissionRole pr = getPermissionRole(permissionId, roleId);
        permissionRoleRepository.delete(pr);
        return true;
    }

    private PermissionRole getPermissionRole(final Long permissionId, final Long roleId) throws  IllegalArgumentException{
        PermissionRole pr = permissionRoleRepository.findByRoleIdAndPermissionId(roleId, permissionId);
        if (pr ==null || pr.getId()<=0){
            throw new IllegalArgumentException("The role does not contain permission id "+permissionId);
        }
        return pr;
    }

}
