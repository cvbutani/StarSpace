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
                            List<MessageTime> sentText = null;
                            if (task.getResult() != null && task.getResult().toObject(ChatMessage.class) != null) {
                                sentText = task.getResult().toObject(ChatMessage.class).getSentMessages();
                            }
                            sendReceiveMessage(message, receiverId, senderId, sentText, "sentMessages");
                        }
                    }
                });

        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(receiverId + senderId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            List<MessageTime> ReceivedText = null;
                            if (task.getResult() != null && task.getResult().toObject(ChatMessage.class) != null) {
                                ReceivedText = task.getResult().toObject(ChatMessage.class).getReceivedMessages();
                            }
                            sendReceiveMessage(message, senderId, receiverId, ReceivedText, "receivedMessages");
                        }
                    }
                });
    }

    private void sendReceiveMessage(String message, String userSRId, String userRSId, List<MessageTime> sentReceiveText, String messageType) {
        if (sentReceiveText == null) {
            HashMap<String, String> messageLabel = new HashMap<>();
            messageLabel.put(messageType, null);
            mFirebaseRepo
                    .getChatDatabaseReferenceInstace()
                    .document(userRSId + userSRId)
                    .set(messageLabel, SetOptions.merge());
        }

        HashMap<String, List<MessageTime>> messageText = new HashMap<>();

        List<MessageTime> textMessage = new ArrayList<>();

        MessageTime text = new MessageTime(message, System.currentTimeMillis());

        if (sentReceiveText != null) {
            textMessage = sentReceiveText;
        }
        textMessage.add(text);
        messageText.put(messageType, textMessage);
        mFirebaseRepo
                .getChatDatabaseReferenceInstace()
                .document(userRSId + userSRId)
                .set(messageText, SetOptions.mergeFields(messageType));
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
                                listMessages = documentSnapshot.toObject(ChatMessage.class).getSentMessages();
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
