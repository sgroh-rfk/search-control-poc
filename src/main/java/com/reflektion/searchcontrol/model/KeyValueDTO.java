package com.reflektion.searchcontrol.model;

import java.math.BigDecimal;

/**
 * @author sebastiangroh@reflektion.com
 */
public class KeyValueDTO {

    private Long id;

    private Long userId;

    private Long keyId;

    private String keyName;

    private String value;

    private String domain;

    private BigDecimal createdTime;

    private Boolean live;

    public KeyValueDTO() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public BigDecimal getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(BigDecimal createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public KeyValue getEntityFromDTO(KeyValueDTO dto) {
        KeyValue kv = new KeyValue();
        kv.setId(dto.getId());
        kv.setCreatedTime(dto.getCreatedTime());
        kv.setDomain(dto.getDomain());
        kv.setKeyName(dto.keyName);
        kv.setLive(dto.getLive());
        kv.setValue(dto.getValue());
        return kv;
    }
}
