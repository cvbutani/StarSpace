package com.example.chiragpc.starspace.ui.friends;

import com.example.chiragpc.starspace.base.MvpView;
import com.example.chiragpc.starspace.model.UserAccount;

import java.util.List;

/**
 * Created by Chirag on 12/28/2018 at 14:32.
 * Project - StarSpace
 */
public interface FriendsContract {
    interface View extends MvpView {
        void friendRequestsReceived(List<UserAccount> userAccountList);

        void friendRequestFailure(String error);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter {
        void getfriendRequest(String userId);
    }
}
