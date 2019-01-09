package com.example.chiragpc.starspace.authentication.register;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

/**
 * Created by Chirag on 12/16/2018 at 14:52.
 * Project - StarSpace
 */
public class RegisterPresenter
        extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private DataManager mDataManager;

    RegisterPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public RegisterContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(RegisterContract.View view) {
        super.attachView(view);
    }

    @Override
    public void registerUser(String username, String email, String password) {
        getView().showProgressBar();
        mDataManager.registerDataRepo(username, email, password, new OnTaskCompletion() {
            @Override
            public void onSuccess(String userId) {
                getView().hideProgressBar();
                getView().registerNewUserSuccess(userId);
            }

            @Override
            public void onFailure(String errorMessage) {
                getView().hideProgressBar();
                getView().registerFailure(errorMessage);
            }
        });
    }
}
