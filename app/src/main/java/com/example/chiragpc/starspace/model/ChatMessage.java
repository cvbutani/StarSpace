package com.example.chiragpc.starspace.model;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Chirag on 1/8/2019 at 19:57.
 * Project - StarSpace
 */
public class ChatMessage {

    @Nullable
    private List<MessageTime> messages;

    public ChatMessage() {
    }

    public ChatMessage(@Nullable List<MessageTime> messages) {
        this.messages = messages;
    }

    @Nullable
    public List<MessageTime> getMessages() {
        return messages;
    }

    public void setSentMessages(@Nullable List<MessageTime> messages) {
        this.messages = messages;
    }
}
