package com.reflektion.searchcontrol.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reflektion.searchcontrol.model.permission.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
@Entity(name = "permission_key")
public class PermissionKey extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    @ApiModelProperty(value = "Unique identifier for the permission.")
    @JsonProperty("permission")
    private Permission permission;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "key_id", referencedColumnName = "id")
    @JsonProperty("key")
    private Key key;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    protected Class<PermissionKey> entityClass() {
        return PermissionKey.class;
    }

}
