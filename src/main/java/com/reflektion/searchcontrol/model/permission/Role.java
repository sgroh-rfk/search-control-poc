package com.reflektion.searchcontrol.model.permission;

import com.reflektion.searchcontrol.model.BaseEntity;
import org.apache.commons.lang3.Validate;
import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Role entiry.
 *
 * WARNING: This entity SHOULD BE PART OF A PERMISSION API.
 *
 * @author sebastiangroh@reflektion.com
 */
@Entity
public class Role extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public Role(Long id, final String theName) {
        Validate.notNull(id, "The role id cannot be null.");
        Validate.notNull(theName, "The role name cannot be null.");
        this.id = id;
        this.name = theName;
    }

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Class<Role> entityClass() {
        return Role.class;
    }
}
