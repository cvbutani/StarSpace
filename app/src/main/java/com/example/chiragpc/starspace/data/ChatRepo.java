package com.example.chiragpc.starspace.data;

import android.util.Log;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

/**
 * Created by Chirag on 1/8/2019 at 19:54.
 * Project - StarSpace
 */
public class ChatRepo {

    private FirebaseRepo mFirebaseRepo;

    ChatRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    void saveMessagesChatRepo(String message, String senderId, String receiverId, OnTaskCompletion.SaveMessages taskCompletion) {
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(senderId + receiverId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            List<MessageTime> chatMessage = null;
                            if (task.getResult() != null && task.getResult().toObject(ChatMessage.class) != null) {
                                chatMessage = task.getResult().toObject(ChatMessage.class).getMessageTimes();
                            }
                            if (chatMessage == null) {
                                HashMap<String, String> messages = new HashMap<>();
                                messages.put("senderId", senderId);
                                messages.put("receiverId", receiverId);
                                messages.put("messageTimes", null);
                                mFirebaseRepo
                                        .getChatDatabaseReferenceInstace()
                                        .document(senderId + receiverId)
                                        .set(messages);
                            }

                            HashMap<String, List<MessageTime>> messageText = new HashMap<>();
                            List<MessageTime> time = new ArrayList<>();
                            MessageTime m = new MessageTime(message, System.currentTimeMillis());
                            if (chatMessage != null) {
                                time = chatMessage;
                            }
                            time.add(m);
                            messageText.put("messageTimes", time);
                            mFirebaseRepo
                                    .getChatDatabaseReferenceInstace()
                                    .document(senderId + receiverId).set(messageText, SetOptions.mergeFields("messageTimes"));
                        }
                    }
                });
    }

    void getMessagesChatRepo(String senderId, String receiverId, OnTaskCompletion.GetMessages taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(senderId + receiverId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            List<MessageTime> listMessages = new ArrayList<>();
                            if (documentSnapshot.toObject(ChatMessage.class) != null) {
                                listMessages = documentSnapshot.toObject(ChatMessage.class).getMessageTimes();
                                taskCompletion.onGetMessagesSuccess(listMessages);
                            }
                        }

                        if (e != null) {
                            taskCompletion.onGetMessagesFailure(e.getMessage());
                        }
                    }
                });
    }
}
