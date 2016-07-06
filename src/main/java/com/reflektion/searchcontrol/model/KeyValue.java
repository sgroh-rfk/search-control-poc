package com.reflektion.searchcontrol.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.reflektion.searchcontrol.model.permission.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringBootServerCodegen", date = "2016-06-29T19:31:03.612Z")
@Entity(name = "key_value")
public class KeyValue extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    @ApiModelProperty(value = "Unique identifier for the user.")
    @JsonProperty("userId")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "key_id")
    @JsonProperty("keyId")
    private Key key;

    @Column(name = "key_name")
    @ApiModelProperty(value = "Unique identifier for the key.")
    @JsonProperty("keyName")
    private String keyName;

    @Column(name = "value")
    @ApiModelProperty(value = "Value.")
    @JsonProperty("value")
    private String value;

    @Column(name = "domain")
    @ApiModelProperty(value = "Domain.")
    @JsonProperty("domain")
    private String domain;

    @Column(name = "createdTime")
    @ApiModelProperty(value = "Creation TimeStamp.")
    @JsonProperty("createdTime")
    private BigDecimal createdTime;

    @Column(name = "live")
    @ApiModelProperty(value = "KeyValue live.")
    @JsonProperty("live")
    private Boolean live = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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


    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    protected Class<KeyValue> entityClass() {
        return KeyValue.class;
    }

    public KeyValue updateWithDTO(KeyValueDTO dto) {
        this.setLive(dto.getLive());
        this.setValue(dto.getValue());
        this.setId(dto.getId());
        this.setKeyName(dto.getKeyName());
        this.setDomain(dto.getDomain());
        this.setCreatedTime(dto.getCreatedTime());
        return this;
    }
}
