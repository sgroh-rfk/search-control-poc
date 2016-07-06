package com.reflektion.searchcontrol.model;

/**
 * @author sebastiangroh@reflektion.com
 */
public class KeyDTO {
    private Long id = null;
    private Long parentId =null;
    private String description;
    private String name;
    private String schema;

    public KeyDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Key getEntityFromDTO(){
        Key k = new Key();
        k.setId(this.getId());
        k.setDescription(this.getDescription());
        k.setName(this.getName());
        k.setSchema(this.schema);
        if (this.parentId!=null){
            Key parent = new Key();
            parent.setId(this.parentId);
            k.setParentKey(parent);
        }
        else
            k.setParentKey(null);
        return k;
    }
}
