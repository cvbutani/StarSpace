package com.example.chiragpc.starspace.data.callbacks;

import com.example.chiragpc.starspace.model.UserAccount;

public interface OnTaskCompletion {

    void onSuccess(String userId);

    void onFailure(String errorMessage);

    interface ResetPassword {
        void onResetPasswordSuccess();

        void onResetPasswrodFailure(String error);
    }

    interface UserAccountInfo {
        void onCurrentUserInfoSuccess(UserAccount account);

        void onCurrentUserInfoFailure(String error);
    }
}
