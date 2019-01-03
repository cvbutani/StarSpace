package com.example.chiragpc.starspace.ui.profile;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.UserAccount;

/**
 * Created by Chirag on 1/1/2019 at 16:56.
 * Project - StarSpace
 */
public interface ProfileContract {
    interface View extends MvpView {

        void getCurrentuser(UserAccount account);

        void friendRequestStatus(boolean isSuccess);

        void getCurrentUserFailure(String error);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {

        void userAccount(String userId);

        void friendRequest (boolean isFriend, String senderUserId, String receiverUserId);
    }
}
