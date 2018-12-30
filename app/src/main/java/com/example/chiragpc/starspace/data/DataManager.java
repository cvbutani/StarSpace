package com.example.chiragpc.starspace.data;

import android.net.Uri;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;

public class DataManager implements DataContract {

    private AccountRepo mAccountRepo;
    private FriendRepo mFriendRepo;
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
        FirebaseRepo firebaseRepo = new FirebaseRepo();
        mAccountRepo = new AccountRepo(firebaseRepo);
        mFriendRepo = new FriendRepo(firebaseRepo);
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
    public void allUserAccountDataRepo(String userId, OnTaskCompletion.userRegisteredInfo taskCompletion) {
        mAccountRepo.userRegisteredInfo(userId, taskCompletion);
    }

    @Override
    public void userAccountByUId(String uId, OnTaskCompletion.UserByUniqueId taskCompletion) {
        mFriendRepo.userAccountByUId(uId, taskCompletion);
    }

    @Override
    public void sendFriendRequest(boolean isFriend, String senderUId, String receiverUId, OnTaskCompletion.FriendRequest taskCompletion) {
        mFriendRepo.sendFriendRequest(isFriend, senderUId, receiverUId, taskCompletion);
    }

    @Override
    public void profilePicDataRepo(String userId, Uri imageUri) {
        mAccountRepo.uploadUserProfilePic(userId, imageUri);
    }

    @Override
    public void userFriendRequestDataRepo(String userId, OnTaskCompletion.UserFriendRequestInfo taskCompletion) {
        mFriendRepo.oldFriendAndNewFriendRequest(userId, taskCompletion);
    }

    @Override
    public void accceptFriendRequest(String accountId, String requesterId, int userResponse, OnTaskCompletion.FriendRequest taskCompletion) {
        mFriendRepo.friendAcceptDeclineUnFriendResponse(accountId, requesterId, userResponse, taskCompletion);
    }

}
