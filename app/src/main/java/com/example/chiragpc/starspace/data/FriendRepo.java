package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.chiragpc.starspace.config.AppConfig.ACCEPT_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.DECLINE_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_RECEIVED;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_SENT;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_STATUS;
import static com.example.chiragpc.starspace.config.AppConfig.UNFRIEND;

/**
 * Created by Chirag on 12/26/2018 at 19:10.
 * Project - StarSpace
 */
class FriendRepo {

    private FirebaseRepo mFirebaseRepo;

    FriendRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    void userAccountByUId(String uId, OnTaskCompletion.UserByUniqueId taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .whereEqualTo("id", uId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<UserAccount> account = task.getResult().toObjects(UserAccount.class);
                        taskCompletion.onUserSuccess(account.get(0));
                    }
                });

    }

    void sendFriendRequest(boolean isFriend, String senderUId, String receiverUId, OnTaskCompletion.FriendRequest taskCompletion) {
        if (!isFriend) {
            sendingFriendRequest(receiverUId, senderUId, FRIEND_REQUEST_SENT, taskCompletion);
            sendingFriendRequest(senderUId, receiverUId, FRIEND_REQUEST_RECEIVED, taskCompletion);
        }
    }

    private void sendingFriendRequest(String senderUId, String receiverUId, String requestType, OnTaskCompletion.FriendRequest taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .whereEqualTo("id", receiverUId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                            UserAccount account = snapshot.toObject(UserAccount.class);
                            List<String> senderId;
                            if (requestType.equals(FRIEND_REQUEST_RECEIVED)) {
                                senderId = account.getRequestReceived();
                            } else {
                                senderId = account.getRequestSent();
                            }

                            if (senderId == null) {
                                senderId = new ArrayList<>();
                            }
                            HashMap<String, List<String>> requestReceived = new HashMap<>();

                            senderId.add(senderUId);
                            requestReceived.put(requestType, senderId);

                            addFriendToDatabase(requestReceived, receiverUId, requestType, taskCompletion);
                        }
                    }

                });
    }

    void oldFriendAndNewFriendRequest(String userId, OnTaskCompletion.UserFriendRequestInfo taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        UserAccount userAccount = task.getResult().toObject(UserAccount.class);

                        if (userAccount != null && userAccount.getFriends() != null) {
                            List<UserAccount> accountList = new ArrayList<>();
                            for (String friends : userAccount.getFriends()) {
                                updateFriendRequest(friends, accountList, FRIEND_STATUS, taskCompletion);
                            }
                        }

                        if (userAccount != null && userAccount.getRequestReceived() != null) {
                            List<UserAccount> accountList = new ArrayList<>();
                            for (String receivedUserId : userAccount.getRequestReceived()) {
                                updateFriendRequest(receivedUserId, accountList, null, taskCompletion);
                            }
                        }
                    }
                });
    }

    private void updateFriendRequest(String id, List<UserAccount> account, String status, OnTaskCompletion.UserFriendRequestInfo taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(id)
                .get()
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful() && task1.getResult() != null) {
                        UserAccount user = task1.getResult().toObject(UserAccount.class);
                        if (status != null) {
                            if (user != null) {
                                user.setType(status);
                            }
                        }
                        account.add(user);
                        taskCompletion.onFriendRequestInfoSuccess(account);
                    }
                });
    }

    void friendAcceptDeclineUnFriendResponse(String accountId, String requesterId, int userResponse, OnTaskCompletion.FriendRequest taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(accountId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        UserAccount userAccount = task.getResult().toObject(UserAccount.class);
                        if (userAccount != null && userAccount.getRequestReceived() != null) {
                            for (String receiverUserId : userAccount.getRequestReceived()) {
                                if (requesterId.equals(receiverUserId)) {
                                    //  Current User Database Update
                                    switch (userResponse) {
                                        //  Accept Friend Request
                                        case ACCEPT_REQUEST:
                                            List<String> request = userAccount.getFriends();
                                            if (request == null) {
                                                request = new ArrayList<>();
                                            }
                                            request.add(receiverUserId);

                                            HashMap<String, List<String>> acceptedFriend = new HashMap<>();
                                            acceptedFriend.put(FRIEND_STATUS, request);

                                            addFriendToDatabase(acceptedFriend, accountId, FRIEND_STATUS, taskCompletion);
                                            removeFriendFromDatabase(accountId, FRIEND_REQUEST_RECEIVED, receiverUserId, taskCompletion);

                                            //  Accepted User database update
                                            acceptedFriendUpdateInDatabase(accountId, requesterId, taskCompletion);
                                            break;
                                        //  Decline Friend Request
                                        case DECLINE_REQUEST:
                                            removeFriendFromDatabase(accountId, FRIEND_REQUEST_RECEIVED, receiverUserId, taskCompletion);
                                            removeFriendFromDatabase(receiverUserId, FRIEND_REQUEST_SENT, accountId, taskCompletion);
                                            break;
                                    }
                                }
                            }
                        }
                        //  UNFRIEND USER
                        if (userAccount != null && userAccount.getFriends() != null && userResponse == UNFRIEND) {
                            removeFriendFromDatabase(accountId, FRIEND_STATUS, requesterId, taskCompletion);
                            removeFriendFromDatabase(requesterId, FRIEND_STATUS, accountId, taskCompletion);
                        }
                    }
                });
    }

    private void acceptedFriendUpdateInDatabase(String accountId, String requesterId, OnTaskCompletion.FriendRequest taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(requesterId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        UserAccount userAccounts = task.getResult().toObject(UserAccount.class);
                        if (userAccounts != null && userAccounts.getRequestSent() != null) {
                            for (String sentUserId : userAccounts.getRequestSent()) {
                                if (accountId.equals(sentUserId)) {
                                    List<String> sent = userAccounts.getFriends();
                                    if (sent == null) {
                                        sent = new ArrayList<>();
                                    }
                                    sent.add(sentUserId);
                                    HashMap<String, List<String>> acceptedFriend = new HashMap<>();
                                    acceptedFriend.put(FRIEND_STATUS, sent);

                                    addFriendToDatabase(acceptedFriend, requesterId, FRIEND_STATUS, taskCompletion);
                                    removeFriendFromDatabase(requesterId, FRIEND_REQUEST_SENT, accountId, taskCompletion);

                                }
                            }
                        }
                    }
                });
    }

    private void addFriendToDatabase(HashMap<String, List<String>> request, String id, String requester, OnTaskCompletion.FriendRequest taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(id)
                .set(request, SetOptions.mergeFields(requester))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskCompletion.onFriendRequestSuccess(task.isSuccessful());
                    }
                }).addOnFailureListener(e -> taskCompletion.onFriendRequestFailure(e.getMessage()));
    }

    private void removeFriendFromDatabase(String id, String requester, String userIdRemove, OnTaskCompletion.FriendRequest taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(id)
                .update(requester, FieldValue.arrayRemove(userIdRemove))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskCompletion.onFriendRequestSuccess(task.isSuccessful());
                    }
                }).addOnFailureListener(e -> taskCompletion.onFriendRequestFailure(e.getMessage()));
    }
}
