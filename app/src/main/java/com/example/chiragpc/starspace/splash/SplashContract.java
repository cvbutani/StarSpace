package com.example.chiragpc.starspace.splash;

import com.example.chiragpc.starspace.base.MvpView;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Chirag on 1/8/2019 at 19:25.
 * Project - StarSpace
 */
public interface SplashContract {
    interface View extends MvpView {
        void currentUserAcc(FirebaseUser user);
    }

    interface Presenter {
        void userAccount();
    }
}
