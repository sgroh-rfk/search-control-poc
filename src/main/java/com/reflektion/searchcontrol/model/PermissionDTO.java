package com.reflektion.searchcontrol.model;

/**
 * @author sebastiangroh@reflektion.com
 */
public class PermissionDTO {
    private Long id;

    private String permission;

    public PermissionDTO(){}

    public PermissionDTO(final Long id, final String permission){
        this.id = id;
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Permission getEntityFromDTO(final PermissionDTO dto){
        Permission p = new Permission();
        p.setId(dto.getId());
        p.setPermission(dto.getPermission());
        return p;
    }

}
