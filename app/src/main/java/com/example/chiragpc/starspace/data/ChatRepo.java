package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    void saveMessagesChatRepo(ChatMessage message, OnTaskCompletion.SaveMessages taskCompletion) {
        mFirebaseRepo
                .getChatDatabaseReferenceInstace()
                .document()
                .set(message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            taskCompletion.onSaveMessageSuccess(task.isSuccessful());
                        } else {
                            taskCompletion.onSaveMessageFailure(task.getException().getMessage());
                        }
                    }
                });
    }
}
