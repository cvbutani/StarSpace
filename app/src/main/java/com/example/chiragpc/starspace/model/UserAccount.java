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
    private List<String> friendRequestReceived;

    @Nullable
    private List<String> friendRequestSent;

    public UserAccount() {
    }

    public UserAccount(String id, String username, @Nullable List<String> friendRequestReceived, @Nullable List<String> friendRequestSender) {
        this.id = id;
        this.username = username;
        this.friendRequestReceived = friendRequestReceived;
        this.friendRequestSent = friendRequestSender;
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
    public List<String> getFriendRequestReceived() {
        return friendRequestReceived;
    }

    public void setFriendRequestReceived(@Nullable List<String> friendRequestReceived) {
        this.friendRequestReceived = friendRequestReceived;
    }

    @Nullable
    public List<String> getFriendRequestSender() {
        return friendRequestSent;
    }

    public void setFriendRequestSender(@Nullable List<String> friendRequestSender) {
        this.friendRequestSent = friendRequestSender;
    }
}
