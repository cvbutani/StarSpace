package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_RECEIVER;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_SENDER;

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
                                    List<String> receiverid = account.getFriendRequestReceived();

                                    HashMap<String, List<String>> requestSent = new HashMap<>();
                                    if (receiverid == null) {
                                        receiverid = new ArrayList<>();
                                    }
                                    receiverid.add(receiverUId);
                                    requestSent.put(FRIEND_REQUEST_SENDER, receiverid);

                                    addFriendToDatabase(requestSent, senderUId, taskCompletion);
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
                                    List<String> senderId = account.getFriendRequestSender();

                                    HashMap<String, List<String>> requestReceived = new HashMap<>();
                                    if (senderId == null) {
                                        senderId = new ArrayList<>();
                                    }
                                    senderId.add(senderUId);
                                    requestReceived.put(FRIEND_REQUEST_RECEIVER, senderId);

                                    addFriendToDatabase(requestReceived, receiverUId, taskCompletion);
                                }
                            }

                        }
                    });
        }
    }

    private void addFriendToDatabase(HashMap<String, List<String>> request, String id, OnTaskCompletion.FriendRequest taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(id)
                .set(request, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            taskCompletion.onFriendRequestSuccess(task.isSuccessful());
                        }
                    }
                });
    }
}
