package com.example.chiragpc.starspace.ui.profile;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

/**
 * Created by Chirag on 1/1/2019 at 16:56.
 * Project - StarSpace
 */
public class ProfilePresenter
        extends BasePresenter<ProfileContract.View>
        implements ProfileContract.Presenter {

    private DataManager mDataManager;

    public ProfilePresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public ProfileContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(ProfileContract.View view) {
        super.attachView(view);
    }

    @Override
    public void userAccount(String userId) {
        getView().showProgressBar();
        mDataManager.userAccountDataRepo(userId, new OnTaskCompletion.UserAccountInfo() {
            @Override
            public void onCurrentUserInfoSuccess(UserAccount account) {
                getView().hideProgressBar();
                getView().getCurrentuser(account);
            }

            @Override
            public void onCurrentUserInfoFailure(String error) {
                getView().hideProgressBar();
                getView().getCurrentUserFailure(error);
            }
        });
    }

    @Override
    public void authorizedUserAccount(String userId) {
        getView().showProgressBar();
        mDataManager.userAccountDataRepo(userId, new OnTaskCompletion.UserAccountInfo() {
            @Override
            public void onCurrentUserInfoSuccess(UserAccount account) {
                getView().hideProgressBar();
                getView().getAuthorizedUser(account);
            }

            @Override
            public void onCurrentUserInfoFailure(String error) {
                getView().hideProgressBar();
                getView().getCurrentUserFailure(error);
            }
        });
    }

    @Override
    public void friendRequest(boolean isFriend, String senderUserId, String receiverUserId) {
        getView().showProgressBar();
        mDataManager.sendFriendRequest(isFriend, senderUserId, receiverUserId, new OnTaskCompletion.FriendRequest() {
            @Override
            public void onFriendRequestSuccess(boolean isSuccess) {
                getView().hideProgressBar();
                getView().friendRequestStatus(isSuccess);
            }

            @Override
            public void onFriendRequestFailure(String error) {
                getView().hideProgressBar();
                getView().getCurrentUserFailure(error);
            }
        });
    }

    @Override
    public void friendRequestResponse(String accountId, String requestId, int userResponse) {
        getView().showProgressBar();
        mDataManager.accceptFriendRequest(accountId, requestId, userResponse, new OnTaskCompletion.FriendRequest() {
            @Override
            public void onFriendRequestSuccess(boolean isSuccess) {
                getView().hideProgressBar();
                getView().friendRequestStatus(isSuccess);
            }

            @Override
            public void onFriendRequestFailure(String error) {
                getView().hideProgressBar();
                getView().getCurrentUserFailure(error);
            }
        });
    }
}
