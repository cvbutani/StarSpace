package com.example.chiragpc.starspace.model;

/**
 * Created by Chirag on 12/18/2018 at 19:25.
 * Project - StarSpace
 */
public class UserAccount {
    private String id;

    private String username;

    public UserAccount() {
    }

    public UserAccount(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
