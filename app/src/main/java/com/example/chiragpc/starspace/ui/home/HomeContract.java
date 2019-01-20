package com.example.chiragpc.starspace.ui.home;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

import java.util.List;

/**
 * Created by Chirag on 12/18/2018 at 19:47.
 * Project - StarSpace
 */
public interface HomeContract {
    interface View extends MvpView {
        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void userAccount(String userId);

        void lastMessage(String senderId, String receiverId);

    }
}
