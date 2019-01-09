package com.example.chiragpc.starspace.ui.chat;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.ChatMessage;

/**
 * Created by Chirag on 1/8/2019 at 20:06.
 * Project - StarSpace
 */
public interface ChatContract {
    interface View extends MvpView {
        void sendingMessageSuccess(boolean isSuccess);

        void sendingMessageFailure(String error);
    }

    interface Presenter {
        void sendMessage(ChatMessage message);
    }
}
