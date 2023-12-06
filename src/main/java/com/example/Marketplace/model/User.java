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
    int id;
    String username;
    String pw;

    public User() {
    }

    public User(int id, String username, String pw) {
        this.id = id;
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
