package com.example.chiragpc.starspace.ui.settings;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.UserAccount;

/**
 * Created by Chirag on 12/17/2018 at 21:19.
 * Project - StarSpace
 */
public interface SettingsContract {
    interface View extends MvpView {
        void logoutSuccess();

        void getCurrentuser(UserAccount account);

        void getCurrentUserFailure(String error);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void logout();

        void userAccount(String userId);
    }
}
