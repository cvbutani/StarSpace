package com.example.chiragpc.starspace.data;

import android.net.Uri;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;

import java.util.List;

public interface DataContract {

    void currentUserDataRepo(OnTaskCompletion.CurrentUserInfo taskCompletion);

    void signInDataRepo(String email, String password, OnTaskCompletion taskCompletion);

    void registerDataRepo(String username, String email, String password, OnTaskCompletion taskCompletion);

    void resetPasswordDataRepo(String email, OnTaskCompletion.ResetPassword taskCompletion);

    void signOutDataRepo();

    void userAccountDataRepo(String userID, OnTaskCompletion.UserAccountInfo taskCompletion);

    void allUserAccountDataRepo(String userId, OnTaskCompletion.userRegisteredInfo taskCompletion);

    void userAccountByUId(String uId, OnTaskCompletion.UserByUniqueId taskCompletion);

    void sendFriendRequest(boolean isFriend, String senderUId, String receiverUId, OnTaskCompletion.FriendRequest taskCompletion);

    void profilePicDataRepo(String userId, Uri imageUri);

    void userFriendRequestDataRepo(String userId, OnTaskCompletion.UserFriendRequestInfo taskCompletion);

    void accceptFriendRequest(String accountId, String requesterId, int userResponse, OnTaskCompletion.FriendRequest taskCompletion);

    void saveMessagesDataRepo(String message, String senderId, String receiverId);

    void getMessagesDataRepo(String senderId, String receiverId, OnTaskCompletion.GetMessages taskCompletion);

    void getLastMessageDataRepo(String senderID, List<String> friends, OnTaskCompletion.GetLastMessages taskCompletion);
}
