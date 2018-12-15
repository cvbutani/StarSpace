package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

public interface DataContract {

    void signInDataRepo(String email, String password, OnTaskCompletion taskCompletion);
}
