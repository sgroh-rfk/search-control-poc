package com.reflektion.searchcontrol.model.permission;


import com.reflektion.searchcontrol.model.BaseEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * User entiry.
 *
 * WARNING: This entity SHOULD BE PART OF A PERMISSION API.
 * @author sebastiangroh@reflektion.com
 */

@Entity(name = "user")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}),
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Column
    private boolean enabled=true;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        if (roles == null) {
            return null;
        }
        return Collections.unmodifiableSet(roles);
    }

    public void replaceRoles(Collection<Role> rolesToAdd) {
        if (roles == null) {
            roles = new HashSet<Role>();
        }
        roles.retainAll(rolesToAdd);
        roles.addAll(rolesToAdd);
    }

    @Override
    protected Class<User> entityClass() {
        return User.class;
    }

}