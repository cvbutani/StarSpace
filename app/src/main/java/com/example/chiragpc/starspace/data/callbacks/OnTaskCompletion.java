package com.example.chiragpc.starspace.data.callbacks;

public interface OnTaskCompletion {

    void onSuccess();

    void onFailure(String errorMessage);

    interface ResetPassword {
        void onResetPasswordSuccess();

        void onResetPasswrodFailure(String error);
    }
}
