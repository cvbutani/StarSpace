package com.example.chiragpc.starspace.model;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Chirag on 1/8/2019 at 19:57.
 * Project - StarSpace
 */
public class ChatMessage {

    @Nullable
    private List<MessageTime> messageTimes;
    @Nullable
    private String imageUrl;
    @Nullable
    private String senderId;
    @Nullable
    private String receiverId;

    public ChatMessage() {
    }

    public ChatMessage(@Nullable List<MessageTime> messageTimes, @Nullable String imageUrl, @Nullable String senderId, @Nullable String receiverId) {
        this.messageTimes = messageTimes;
        this.imageUrl = imageUrl;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    @Nullable
    public List<MessageTime> getMessageTimes() {
        return messageTimes;
    }

    public void setMessageTimes(@Nullable List<MessageTime> messageTimes) {
        this.messageTimes = messageTimes;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(@Nullable String senderId) {
        this.senderId = senderId;
    }

    @Nullable
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(@Nullable String receiverId) {
        this.receiverId = receiverId;
    }
}
