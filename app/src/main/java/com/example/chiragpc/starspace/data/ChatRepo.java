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

    void saveMessagesChatRepo(String message, String senderId, String receiverId) {
        //  Add Messages in Sender User Firestore Account
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(senderId + receiverId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        sendReceiveMessage(message, receiverId, senderId, task, "sentMessages");
                    }
                });
        //  Add Messages in Receiver User Firestore Account
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(receiverId + senderId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        sendReceiveMessage(message, senderId, receiverId, task, "receivedMessages");
                    }
                });
    }

    private void sendReceiveMessage(String message,
                                    String senderReceiverId,
                                    String ReceiverSenderId,
                                    Task<DocumentSnapshot> task,
                                    String messageType) {

        if (task.isSuccessful()) {
            List<MessageTime> sentReceiveText = null;
            if (task.getResult() != null && task.getResult().toObject(ChatMessage.class) != null) {
                sentReceiveText = task.getResult().toObject(ChatMessage.class).getMessages();
            }
            if (sentReceiveText == null) {
                HashMap<String, String> messageLabel = new HashMap<>();
                messageLabel.put("messages", null);
                mFirebaseRepo
                        .getChatDatabaseReferenceInstace()
                        .document(ReceiverSenderId + senderReceiverId)
                        .set(messageLabel, SetOptions.merge());
            }

            HashMap<String, List<MessageTime>> messageText = new HashMap<>();

            List<MessageTime> textMessage = new ArrayList<>();

            switch (messageType) {
                case "sentMessages":
                    messageType = "sentMessages";
                    break;
                case "receivedMessages":
                    messageType = "receivedMessages";
                    break;
            }

            MessageTime text = new MessageTime(message, System.currentTimeMillis(), messageType, ReceiverSenderId, senderReceiverId);

            if (sentReceiveText != null) {
                textMessage = sentReceiveText;
            }
            textMessage.add(text);
            messageText.put("messages", textMessage);

            mFirebaseRepo
                    .getChatDatabaseReferenceInstace()
                    .document(ReceiverSenderId + senderReceiverId)
                    .set(messageText, SetOptions.mergeFields("messages"));
        }
    }

    void getMessagesChatRepo(String senderId, String receiverId, OnTaskCompletion.GetMessages taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(senderId + receiverId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            if (documentSnapshot.toObject(ChatMessage.class) != null) {
                                List<MessageTime> listMessages = documentSnapshot.toObject(ChatMessage.class).getMessages();
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
