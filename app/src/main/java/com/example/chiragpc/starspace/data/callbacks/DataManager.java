package com.example.chiragpc.starspace.data.callbacks;

import com.example.chiragpc.starspace.data.AccountRepo;
import com.example.chiragpc.starspace.data.DataContract;
import com.example.chiragpc.starspace.data.FirebaseRepo;

public class DataManager implements DataContract {

    private AccountRepo mAccountRepo;
    private FirebaseRepo mFirebaseRepo;

    public DataManager() {
        mFirebaseRepo = new FirebaseRepo();
        mAccountRepo = new AccountRepo(mFirebaseRepo);
    }

    @Override
    public void signInDataRepo(String email, String password, OnTaskCompletion taskCompletion) {
        mAccountRepo.signInAccRepo(email, password, taskCompletion);
    }
}
