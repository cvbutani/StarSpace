package com.example.chiragpc.starspace.splash;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Chirag on 1/8/2019 at 19:26.
 * Project - StarSpace
 */
public class SplashPresenter extends BasePresenter<SplashContract.View>
        implements SplashContract.Presenter{

    private DataManager mDataManager;

    public SplashPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public SplashContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);
        userAccount();
    }

    @Override
    public void userAccount() {
        mDataManager.currentUserDataRepo(new OnTaskCompletion.CurrentUserInfo() {
            @Override
            public void onCurrentUserSuccess(FirebaseUser user) {
                getView().currentUserAcc(user);
            }
        });
    }
}
