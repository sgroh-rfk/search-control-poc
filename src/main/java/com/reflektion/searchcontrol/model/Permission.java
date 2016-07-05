package com.reflektion.searchcontrol.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reflektion.searchcontrol.model.permission.Role;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * Permission entity.
 *
 * @author sebastiangroh@reflektion.com
 */
@Entity(name = "permission")
public class Permission extends BaseEntity {

    @Column(name = "permission", unique = true)
    private String permission;

    public Permission(final Long id, final String permission){
        super(id);
        this.permission = permission;
    }

    @OneToMany(mappedBy = "role")
    @JsonProperty("PermissionRole")
    private Set<PermissionRole> roles;

    @OneToMany(mappedBy = "key")
    @JsonProperty("PermissionKey")
    private Set<PermissionKey> keys;

    public Set<PermissionKey> getKeys() {
        return keys;
    }

    public void setKeys(Set<PermissionKey> keys) {
        this.keys = keys;
    }

    public Set<PermissionRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PermissionRole> roles) {
        this.roles = roles;
    }

    public Permission(){}

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    @Override
    protected Class<Permission> entityClass() {
        return Permission.class;
    }
}
