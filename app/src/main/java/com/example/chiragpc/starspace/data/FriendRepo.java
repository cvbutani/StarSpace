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

import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_RECEIVED;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_SENT;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_STATUS;

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
            mFirebaseRepo
                    .getUserDatabaseReferenceInstance()
                    .whereEqualTo("id", senderUId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserAccount account = snapshot.toObject(UserAccount.class);
                                List<String> receiverid = account.getRequestSent();

                                if (receiverid == null) {
                                    receiverid = new ArrayList<>();
                                }

                                HashMap<String, List<String>> requestSent = new HashMap<>();

                                receiverid.add(receiverUId);
                                requestSent.put(FRIEND_REQUEST_SENT, receiverid);

                                addFriendToDatabase(requestSent, senderUId, FRIEND_REQUEST_SENT, taskCompletion);
                            }
                        }
                    });

            mFirebaseRepo
                    .getUserDatabaseReferenceInstance()
                    .whereEqualTo("id", receiverUId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserAccount account = snapshot.toObject(UserAccount.class);
                                List<String> senderId = account.getRequestReceived();

                                HashMap<String, List<String>> requestReceived = new HashMap<>();
                                if (senderId == null) {
                                    senderId = new ArrayList<>();
                                }
                                senderId.add(senderUId);
                                requestReceived.put(FRIEND_REQUEST_RECEIVED, senderId);

                                addFriendToDatabase(requestReceived, receiverUId, FRIEND_REQUEST_RECEIVED, taskCompletion);
                            }
                        }

                    });
        }
    }

    void friendRequestReceived(String userId, OnTaskCompletion.UserFriendRequestInfo taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        UserAccount userAccount = task.getResult().toObject(UserAccount.class);

                        if (userAccount != null && userAccount.getRequestReceived() != null) {
                            List<UserAccount> accountList = new ArrayList<>();

                            if (userAccount.getFriends() != null) {
                                for (String friends : userAccount.getFriends()) {
                                    mFirebaseRepo
                                            .getUserDatabaseReferenceInstance()
                                            .document(friends)
                                            .get()
                                            .addOnCompleteListener(newTask -> {
                                                if (newTask.isSuccessful() && newTask.getResult() != null) {
                                                    UserAccount user = newTask.getResult().toObject(UserAccount.class);
                                                    if (user != null) {
                                                        user.setType(FRIEND_STATUS);
                                                    }
                                                    accountList.add(user);
                                                    taskCompletion.onFriendRequestInfoSuccess(accountList);
                                                }
                                            });
                                }
                            }

                            for (String receivedUserId : userAccount.getRequestReceived()) {
                                Logger.i(receivedUserId);
                                mFirebaseRepo
                                        .getUserDatabaseReferenceInstance()
                                        .document(receivedUserId)
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful() && task1.getResult() != null) {
                                                UserAccount user = task1.getResult().toObject(UserAccount.class);
                                                accountList.add(user);
                                                taskCompletion.onFriendRequestInfoSuccess(accountList);
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    void accceptFriendRequest(String accountId, String requesterId, OnTaskCompletion.FriendRequest taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
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
                                    addAcceptedFriendToDatabase(accountId, requesterId, taskCompletion);

                                }
                            }
                        }
                    }
                });
    }

    private void addAcceptedFriendToDatabase(String accountId, String requesterId, OnTaskCompletion.FriendRequest taskCompletion) {
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
