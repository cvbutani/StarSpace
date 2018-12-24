package com.example.chiragpc.starspace.ui.user;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

import java.util.List;

/**
 * Created by Chirag on 12/23/2018 at 16:34.
 * Project - StarSpace
 */
public class AllUsersPresenter
        extends BasePresenter<AllUsersContract.View>
        implements AllUsersContract.Presenter {

    DataManager mDataManager;

    AllUsersPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public AllUsersContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(AllUsersContract.View view) {
        super.attachView(view);
    }

    @Override
    public void allUser() {
        getView().showProgressBar();
        mDataManager.allUserAccountDataRepo(new OnTaskCompletion.userRegisteredInfo() {
            @Override
            public void onAllUserInfoSuccess(List<UserAccount> useraccount) {
                getView().hideProgressBar();
                getView().allUserSuccess(useraccount);
            }

            @Override
            public void onAllUserInfoFailure(String error) {
                getView().hideProgressBar();
                getView().allUserFailure(error);
            }
        });
    }
}
