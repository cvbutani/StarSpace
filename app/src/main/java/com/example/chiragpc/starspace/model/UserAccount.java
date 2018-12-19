package com.example.chiragpc.starspace.model;

/**
 * Created by Chirag on 12/18/2018 at 19:25.
 * Project - StarSpace
 */
public class UserAccount {
    private String id;

    private String userName;

    public UserAccount() {
    }

    public UserAccount(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
