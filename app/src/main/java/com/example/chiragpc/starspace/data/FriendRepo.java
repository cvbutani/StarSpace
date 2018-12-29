package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_RECEIVED;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_SENT;

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
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<UserAccount> account = task.getResult().toObjects(UserAccount.class);
                            taskCompletion.onUserSuccess(account.get(0));
                        }
                    }
                });

    }

    void sendFriendRequest(boolean isFriend, String senderUId, String receiverUId, OnTaskCompletion.FriendRequest taskCompletion) {
        if (!isFriend) {
            mFirebaseRepo
                    .getUserDatabaseReferenceInstance()
                    .whereEqualTo("id", senderUId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                        }
                    });

            mFirebaseRepo
                    .getUserDatabaseReferenceInstance()
                    .whereEqualTo("id", receiverUId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
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

                        }
                    });
        }
    }

    private void addFriendToDatabase(HashMap<String, List<String>> request, String id, String requester, OnTaskCompletion.FriendRequest taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(id)
                .set(request, SetOptions.mergeFields(requester))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            taskCompletion.onFriendRequestSuccess(task.isSuccessful());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Logger.i(e.getMessage());
            }
        });
    }

    void friendRequestReceived(String userId, OnTaskCompletion.UserFriendRequestInfo taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            UserAccount userAccount = task.getResult().toObject(UserAccount.class);

                            if (userAccount != null && userAccount.getRequestReceived() != null) {
                                List<UserAccount> accountList = new ArrayList<>();

                                for (String receivedUserId : userAccount.getRequestReceived()) {
                                    Logger.i(receivedUserId);
                                    mFirebaseRepo
                                            .getUserDatabaseReferenceInstance()
                                            .document(receivedUserId)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful() && task.getResult() != null) {
                                                        UserAccount user = task.getResult().toObject(UserAccount.class);
                                                        accountList.add(user);
                                                        taskCompletion.onFriendRequestInfoSuccess(accountList);
                                                    }
                                                }
                                            });
                                }
                            }
                        }
                    }
                });
    }

    void accceptFriendRequest(String accountId, String requesterId, OnTaskCompletion.FriendRequestAccepted taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(accountId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            UserAccount userAccount = task.getResult().toObject(UserAccount.class);

                            if (userAccount != null && userAccount.getRequestReceived() != null) {
                                List<String> request = new ArrayList<>();
                                for (String receiverUserId : userAccount.getRequestReceived()) {
                                    if (requesterId.equals(receiverUserId)) {

                                        HashMap<String, String> acceptedFriend = new HashMap<>();
                                        acceptedFriend.put("friends", receiverUserId);

                                        mFirebaseRepo
                                                .getUserDatabaseReferenceInstance()
                                                .document(accountId)
                                                .set(acceptedFriend, SetOptions.mergeFields("friends"))
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                        }
                                                    }
                                                });

                                    }
                                    request.add(receiverUserId);
                                    HashMap<String, List<String>> requestNotAccepted = new HashMap<>();
                                    requestNotAccepted.put(FRIEND_REQUEST_RECEIVED, request);

                                    mFirebaseRepo
                                            .getUserDatabaseReferenceInstance()
                                            .document(accountId)
                                            .set(requestNotAccepted, SetOptions.mergeFields(FRIEND_REQUEST_RECEIVED))
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                    }
                                                }
                                            });


                                }
                            }
                        }
                    }
                });
    }
}
