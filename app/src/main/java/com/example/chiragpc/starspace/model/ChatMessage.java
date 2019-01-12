package com.example.chiragpc.starspace.model;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Chirag on 1/8/2019 at 19:57.
 * Project - StarSpace
 */
public class ChatMessage {

    @Nullable
    private List<MessageTime> sentMessages;
    @Nullable
    private List<MessageTime> receivedMessages;

    public ChatMessage() {
    }

    public ChatMessage(@Nullable List<MessageTime> sentMessages, @Nullable List<MessageTime> receivedMessages) {
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
    }

    @Nullable
    public List<MessageTime> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(@Nullable List<MessageTime> sentMessages) {
        this.sentMessages = sentMessages;
    }

    @Nullable
    public List<MessageTime> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(@Nullable List<MessageTime> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
}
