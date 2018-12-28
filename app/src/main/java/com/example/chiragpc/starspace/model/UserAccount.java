package com.example.chiragpc.starspace.model;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Chirag on 12/18/2018 at 19:25.
 * Project - StarSpace
 */
public class UserAccount {
    private String id;

    private String username;

    @Nullable
    private List<String> requestSent;

    @Nullable
    private List<String> requestReceived;

    public UserAccount() {
    }

    public UserAccount(String id, String username, @Nullable List<String> requestSent, @Nullable List<String> requestReceived) {
        this.id = id;
        this.username = username;
        this.requestSent = requestSent;
        this.requestReceived = requestReceived;
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

    @Nullable
    public List<String> getRequestSent() {
        return requestSent;
    }

    public void setRequestSent(@Nullable List<String> requestSent) {
        this.requestSent = requestSent;
    }

    @Nullable
    public List<String> getRequestReceived() {
        return requestReceived;
    }

    public void setRequestReceived(@Nullable List<String> requestReceived) {
        this.requestReceived = requestReceived;
    }
}
