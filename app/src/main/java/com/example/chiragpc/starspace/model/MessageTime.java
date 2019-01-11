package com.example.chiragpc.starspace.model;

/**
 * Created by Chirag on 1/9/2019 at 22:10.
 * Project - StarSpace
 */
public class MessageTime {
    private String textMessage;
    private long timestamp;

    public MessageTime() {
    }

    public MessageTime(String textMessage, long timestamp) {
        this.textMessage = textMessage;
        this.timestamp = timestamp;
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
}
