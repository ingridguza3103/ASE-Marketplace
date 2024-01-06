package com.example.Marketplace.model;

import jakarta.persistence.*;

/**
 * User Entity class created with tablename users in the database
 */
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String pw;

    public User() {
    }

    public User(String username, String pw) {
        this.username = username;
        this.pw = pw;
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "User [username = " + username +  "]";
    }
}
