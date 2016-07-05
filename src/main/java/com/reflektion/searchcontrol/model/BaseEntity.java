package com.reflektion.searchcontrol.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author sebastiangroh@reflektion.com
 */
@MappedSuperclass
@ApiModel
public abstract class BaseEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8054184286041932202L;
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(required = true, value="Object identifier")
    protected Long id;

    public BaseEntity() {
        id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    /**
     * Subclasses must provide their Class definition through this method. <code>equals</code> and <code>hashCode</code>
     * implementations rely on this class.
     *
     * @return the entity class.
     */
    protected abstract Class<? extends BaseEntity> entityClass();

    @Override
    public final boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (getId() != null && entityClass().isInstance(obj)) {
            BaseEntity other = (BaseEntity) obj;
            return getId().equals(other.getId())
                    // for symmetric constraint
                    && entityClass().equals(other.entityClass());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        if (getId() != null) {
            return entityClass().hashCode() + 7 * getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return entityClass().getSimpleName() + "(id:" + getId() + ")";
    }
}
