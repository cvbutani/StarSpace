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

    @Nullable
    private String profilePic;

    public UserAccount() {
    }

    public UserAccount(String id, String username, @Nullable List<String> requestSent, @Nullable List<String> requestReceived, @Nullable String profilePic) {
        this.id = id;
        this.username = username;
        this.requestSent = requestSent;
        this.requestReceived = requestReceived;
        this.profilePic = profilePic;
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

    @Nullable
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(@Nullable String profilePic) {
        this.profilePic = profilePic;
    }
}
