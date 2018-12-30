package com.example.chiragpc.starspace.ui.friends;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Chirag on 12/28/2018 at 14:32.
 * Project - StarSpace
 */
public class FriendsPresenter
    extends BasePresenter<FriendsContract.View>
    implements FriendsContract.Presenter{

    private DataManager mDataManager;

    FriendsPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public FriendsContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(FriendsContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getfriendRequest(String userId) {
        getView().showProgressBar();
        mDataManager.userFriendRequestDataRepo(userId, new OnTaskCompletion.UserFriendRequestInfo() {
            @Override
            public void onFriendRequestInfoSuccess(List<UserAccount> account) {
                getView().hideProgressBar();
                getView().friendRequestsReceived(account);
            }

            @Override
            public void onFriendRequestInfoFailure(String error) {
                getView().hideProgressBar();
                getView().friendRequestFailure(error);
            }
        });
    }

    @Override
    public void friendRequestAccepted(String accountId, String requestId, boolean isAccepted) {
        getView().showProgressBar();
        mDataManager.accceptFriendRequest(accountId, requestId, isAccepted, new OnTaskCompletion.FriendRequest() {
            @Override
            public void onFriendRequestSuccess(boolean isSuccess) {
                Logger.addLogAdapter(new AndroidLogAdapter());
                Logger.i("Updated - " + isSuccess);
            }

            @Override
            public void onFriendRequestFailure(String error) {

            }
        });
    }
}
