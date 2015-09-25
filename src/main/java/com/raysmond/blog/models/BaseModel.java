package com.raysmond.blog.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Raysmond on 9/3/15.
 */
@MappedSuperclass
public abstract class BaseModel implements Comparable<BaseModel>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long _id) {
        id = _id;
    }

    @Override
    public int compareTo(BaseModel o) {
        return this.getId().compareTo(o.getId());
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.getId(), ((BaseModel) other).getId());
        return eb.isEquals();
    }

    /**
     * use HashCodeBuilder to calculate a hashcode
     */
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

}