package com.example.chiragpc.starspace.authentication.login;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

public class LoginPresenter
        extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    private DataManager mDataManager;

    LoginPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public LoginContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(LoginContract.View view) {
        super.attachView(view);
    }

    @Override
    public void signInUser(String email, String password) {
        getView().showProgressBar();
        mDataManager.signInDataRepo(email, password, new OnTaskCompletion() {
            @Override
            public void onSuccess() {
                getView().hideProgressBar();
                getView().signInSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                getView().hideProgressBar();
                getView().signInError(errorMessage);
            }
        });
    }
}
