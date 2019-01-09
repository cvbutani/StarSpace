package com.example.chiragpc.starspace.data.callbacks;

import com.example.chiragpc.starspace.model.UserAccount;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public interface OnTaskCompletion {

    void onSuccess(String userId);

    void onFailure(String errorMessage);

    interface CurrentUserInfo {
        void onCurrentUserSuccess(FirebaseUser user);
    }

    interface ResetPassword {
        void onResetPasswordSuccess();

        void onResetPasswrodFailure(String error);
    }

    interface UserAccountInfo {
        void onCurrentUserInfoSuccess(UserAccount account);

        void onCurrentUserInfoFailure(String error);
    }

    interface userRegisteredInfo {
        void onAllUserInfoSuccess(List<UserAccount> useraccount);

        void onAllUserInfoFailure(String error);
    }

    interface FriendRequest {
        void onFriendRequestSuccess(boolean isSuccess);

        void onFriendRequestFailure(String error);
    }

    interface UserByUniqueId {
        void onUserSuccess(UserAccount account);
    }

    interface UserFriendRequestInfo {
        void onFriendRequestInfoSuccess(List<UserAccount> account);

        void onFriendRequestInfoFailure(String error);
    }

    interface FriendRequestAccepted {
        void onFriendRequestAcceptedSuccess(boolean isSuccess);

        void onFriendRequestFailure(String error);
    }

    interface SaveMessages {
        void onSaveMessageSuccess(boolean isSuccess);
        void onSaveMessageFailure(String error);
    }
}
