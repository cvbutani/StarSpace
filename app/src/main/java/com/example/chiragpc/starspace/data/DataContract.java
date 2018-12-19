package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

public interface DataContract {

    void signInDataRepo(String email, String password, OnTaskCompletion taskCompletion);

    void registerDataRepo(String username, String email, String password, OnTaskCompletion taskCompletion);

    void resetPasswordDataRepo(String email, OnTaskCompletion.ResetPassword taskCompletion);

    void signOutDataRepo();

    void userAccountDataRepo(String userID, OnTaskCompletion.UserAccountInfo taskCompletion);
}
