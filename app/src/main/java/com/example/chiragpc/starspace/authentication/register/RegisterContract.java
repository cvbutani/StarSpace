package com.example.chiragpc.starspace.authentication.register;

import com.example.chiragpc.starspace.base.MvpView;

/**
 * Created by Chirag on 12/16/2018 at 14:52.
 * Project - StarSpace
 */
public interface RegisterContract {
    interface View extends MvpView {
        void registerSuccess();

        void registerFailure(String error);

        void showProgressBar();

        void hideProgressBar();

        interface RegisterCallback {
            void invalidEmail(String error);

            void invalidPassword(String error);
        }
    }

    interface Presenter {
        void registerUser(String username, String email, String password);
    }
}
