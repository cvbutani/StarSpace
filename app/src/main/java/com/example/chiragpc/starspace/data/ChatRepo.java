package com.example.chiragpc.starspace.data;

import android.util.Log;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}
