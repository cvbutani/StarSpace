package com.example.chiragpc.starspace.ui.user;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.UserAccount;

import java.util.List;

/**
 * Created by Chirag on 12/23/2018 at 16:34.
 * Project - StarSpace
 */
public interface AllUsersContract {
    interface View extends MvpView {
        void allUserSuccess(List<UserAccount> userAccountList);

        void friendRequestStatus(boolean isSuccess);

        void allUserFailure(String error);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void allUser(String userId);
        void sendFriendRequest (boolean isFriend, String senderUserId, String receiverUserId);

        void  friendRequestResponse(String accountId, String requestId, int userResponse);
    }
}
