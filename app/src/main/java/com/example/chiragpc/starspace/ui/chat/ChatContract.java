package com.example.chiragpc.starspace.ui.chat;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;

import java.util.List;

/**
 * Created by Chirag on 1/8/2019 at 20:06.
 * Project - StarSpace
 */
public interface ChatContract {
    interface View extends MvpView {

        void messageFailure(String error);

        void getMessageSuccess(List<MessageTime> messages);
    }

    interface Presenter {
        void sendMessage(String message, String senderId, String receiverId);
        void getMessage(String senderId, String receiverId);
    }
}
