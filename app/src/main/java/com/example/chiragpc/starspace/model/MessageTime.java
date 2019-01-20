package com.example.chiragpc.starspace.model;

/**
 * Created by Chirag on 1/9/2019 at 22:10.
 * Project - StarSpace
 */
public class MessageTime {
    private String textMessage;
    private long timestamp;
    private String senderId;
    private String receiverId;

    public MessageTime() {
    }

    public MessageTime(String textMessage, long timestamp, String senderId, String receiverId) {
        this.textMessage = textMessage;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
