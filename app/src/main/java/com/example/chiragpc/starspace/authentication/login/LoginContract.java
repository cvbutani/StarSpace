package com.example.chiragpc.starspace.authentication.login;

import com.example.chiragpc.starspace.base.MvpView;

public interface LoginContract {
    interface View extends MvpView {
        void signInSuccess(String userId);

        void resetPasswordSuccess();

        void resetPasswordFailure(String error);

        void signInError(String error);

        void showProgressBar();

        void hideProgressBar();

        interface SignInCallback {
            void invalidEmail(String error);

            void invalidPassword(String error);
        }
    }

    interface Presenter {
        void signInUser(String email, String password);

        void resetPassword(String email);
    }
}
