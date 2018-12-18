package com.example.chiragpc.starspace.ui.settings;

import com.example.chiragpc.starspace.base.MvpView;

/**
 * Created by Chirag on 12/17/2018 at 21:19.
 * Project - StarSpace
 */
public interface SettingsContract {
    interface View extends MvpView {
        void logoutSuccess();

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void logout();
    }
}
