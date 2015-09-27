package com.raysmond.blog.models;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

/**
 * A generic setting model
 *
 * @author Raysmond
 */
@Entity
@Table(name = "settings")
public class Setting extends BaseModel{
    @Column(name = "_key", unique = true, nullable = false)
    private String key;

    @Lob
    @Column(name = "_value")
    private byte[] value;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @PrePersist
    public void prePersist(){
        if (createdAt == null){
            createdAt = new Date();
        }

        updatedAt = new Date();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Serializable getValue() throws IOException, ClassNotFoundException, SQLException {
        ByteArrayInputStream input = new ByteArrayInputStream(value);
        ObjectInputStream stream = null;
        Serializable value = null;
        try{
            stream = new ObjectInputStream(input);
            value = (Serializable) stream.readObject();
            stream.close();
        } finally {
            input.close();
            stream.close();
        }
        return value;
    }

    public void setValue(Serializable value) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(value);
            this.value = bos.toByteArray();
        } finally {
            out.close();
            bos.close();
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
