package com.example.chiragpc.starspace.model;

/**
 * Created by Chirag on 1/8/2019 at 19:57.
 * Project - StarSpace
 */
public class ChatMessage {
    private String textMessage;
    private String imageUrl;

    public ChatMessage(String textMessage, String imageUrl) {
        this.textMessage = textMessage;
        this.imageUrl = imageUrl;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
