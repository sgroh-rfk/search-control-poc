package com.reflektion.searchcontrol.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reflektion.searchcontrol.model.permission.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
@Entity(name = "permission_role")
public class PermissionRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Unique identifier for the permission.")
    @JsonProperty("permission")
    private Permission permission;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonProperty("role")
    private Role role;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    protected Class<PermissionRole> entityClass() {
        return PermissionRole.class;
    }

}
