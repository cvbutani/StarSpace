package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

public class DataManager implements DataContract {

    private AccountRepo mAccountRepo;
    private FirebaseRepo mFirebaseRepo;
    private static DataManager INSTANCE;

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager();
                }
            }
        }
        return INSTANCE;
    }

    private DataManager() {
        mFirebaseRepo = new FirebaseRepo();
        mAccountRepo = new AccountRepo(mFirebaseRepo);
    }

    @Override
    public void signInDataRepo(String email, String password, OnTaskCompletion taskCompletion) {
        mAccountRepo.signInAccRepo(email, password, taskCompletion);
    }

    @Override
    public void registerDataRepo(String username, String email, String password, OnTaskCompletion taskCompletion) {
        mAccountRepo.registerAccRepo(username, email, password, taskCompletion);
    }

    @Override
    public void resetPasswordDataRepo(String email, OnTaskCompletion.ResetPassword taskCompletion) {
        mAccountRepo.resetPasswordAccRepo(email, taskCompletion);
    }

    @Override
    public void signOutDataRepo() {
        mAccountRepo.signOutAccRepo();
    }

    @Override
    public void userAccountDataRepo(String userId, OnTaskCompletion.UserAccountInfo taskCompletion) {
        mAccountRepo.userAccountInstace(userId, taskCompletion);
    }

    @Override
    public void allUserAccountDataRepo(OnTaskCompletion.userRegisteredInfo taskCompletion) {
        mAccountRepo.userRegisteredInfo(taskCompletion);
    }

}
