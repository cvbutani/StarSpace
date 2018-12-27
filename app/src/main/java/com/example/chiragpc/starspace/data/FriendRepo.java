package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_RECEIVER;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_SENDER;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_REQUEST_TYPE;

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

            HashMap<String, String> requestSent = new HashMap<>();
            requestSent.put(FRIEND_REQUEST_TYPE, receiverUId);

            HashMap<String, String> requestReceived = new HashMap<>();
            requestReceived.put(FRIEND_REQUEST_TYPE, FRIEND_REQUEST_SENDER);

            mFirebaseRepo
                    .getUserDatabaseReferenceInstance()
                    .document(senderUId)
                    .set(requestSent, SetOptions.merge())
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
}
