package com.reflektion.searchcontrol.model;

import java.util.Date;

/**
 * Key Value History Entity.
 *
 * @author sebastiangroh@reflektion.com
 */
public class KeyValueHistory {

    private Key key;

    protected Integer userId;

    private String value;

    private Date createTime;

    private String domain;

    private Boolean live;

    //@Column(name = "deleted") still not present in the DB
    private Boolean deleted;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}


