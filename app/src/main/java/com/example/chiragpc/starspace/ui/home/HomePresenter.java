package com.example.chiragpc.starspace.ui.home;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

/**
 * Created by Chirag on 12/18/2018 at 19:46.
 * Project - StarSpace
 */
public class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    private DataManager mDataManager;

    public HomePresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public HomeContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void userAccount(String userId) {
        mDataManager.userAccountDataRepo(userId, new OnTaskCompletion.UserAccountInfo() {
            @Override
            public void onCurrentUserInfoSuccess(UserAccount account) {
                getView().userAccountSuccess(account);
            }

            @Override
            public void onCurrentUserInfoFailure(String error) {
                getView().userAccountFailure(error);
            }
        });
    }
}
