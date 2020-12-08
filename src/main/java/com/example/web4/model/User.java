package com.example.web4.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "users")
@NoArgsConstructor
@Entity
public class User extends SimpleEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private String refreshToken;
    private String role;


    public User(String username, String password) {
        this.username=username;
        this.password=password;
    }
}
