package com.reflektion.searchcontrol.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Key entity.
 *
 * @author sebastiangroh@reflektion.com
 */
@ApiModel(description = "")
@Entity(name = "keytable")
public class Key extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_id")
    @JsonProperty("parentId")
    private Key parentKey;

    @Column(name = "name")
    @ApiModelProperty(value = "Key name.")
    @JsonProperty("name")
    private String name;


    @Column(name = "description")
    @ApiModelProperty(value = "Key description.")
    @JsonProperty("description")
    private String description;

    @Column(name = "\"schema\"")
    @Type(type="text")
    @ApiModelProperty(value = "Key schema.")
    @JsonProperty("schema")
    private String schema;

    @OneToMany(mappedBy = "key", fetch = FetchType.LAZY)
    @JsonProperty("keyValues")
    private java.util.Set<KeyValue> keyValues;

    @OneToMany(mappedBy = "key", fetch = FetchType.LAZY)
    @JsonProperty("PermissionKeys")
    private java.util.Set<PermissionKey> permissions;

    public Key(){}

    public Key(Long id, String name){
        super(id);
        this.name = name;
    }

    public Set<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Set<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }

    public Set<PermissionKey> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionKey> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Key getParentKey() {
        return parentKey;
    }

    public void setParentKey(Key parentKey) {
        this.parentKey = parentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Class<Key> entityClass() {
        return Key.class;
    }

    public Key updateWithDTO(KeyDTO keyDTO) {
        this.setSchema(keyDTO.getSchema());
        this.setDescription(keyDTO.getDescription());
        this.setName(keyDTO.getName());
        return this;
    }
}
