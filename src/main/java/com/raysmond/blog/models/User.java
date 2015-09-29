package com.raysmond.blog.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Raysmond<jiankunlei@gmail.com>
 */
@Entity
@Table(name = "users")
@Getter @Setter
public class User extends BaseModel{
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER  = "ROLE_USER";

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private String role = ROLE_USER;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Collection<Post> posts = new ArrayList<>();

    public User() {

    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
