package com.example.chiragpc.starspace.ui.home;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

/**
 * Created by Chirag on 12/18/2018 at 19:47.
 * Project - StarSpace
 */
public interface HomeContract {
    interface View extends MvpView {
        void userAccountSuccess(UserAccount account);

        void userAccountFailure(String error);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void userAccount(String userId);
    }
}
