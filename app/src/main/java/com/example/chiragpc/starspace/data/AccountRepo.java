package com.example.chiragpc.starspace.data;


import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import androidx.annotation.NonNull;

class AccountRepo {

    private FirebaseRepo mFirebaseRepo;

    AccountRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    void signInAccRepo(String email, String password, OnTaskCompletion taskCompletion) {
        mFirebaseRepo.getFirebaseAuthInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            taskCompletion.onSuccess();
                        } else {
                            taskCompletion.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    void registerAccRepo(String username, String email, String password, OnTaskCompletion taskCompletion) {
        mFirebaseRepo.getFirebaseAuthInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseRepo.getFirebaseAuthInstance().getCurrentUser();
                            if (user != null) {
                                boolean success =
                                        registerUserToDatabase(user, username);
                                if (success) {
                                    taskCompletion.onSuccess();
                                }
                            }
                        }else {
                            taskCompletion.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    void resetPasswordAccRepo(String email, OnTaskCompletion.ResetPassword taskCompletion) {
        mFirebaseRepo.getFirebaseAuthInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            taskCompletion.onResetPasswordSuccess();
                        } else {
                            taskCompletion.onResetPasswrodFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    void signOutAccRepo() {
        mFirebaseRepo.getFirebaseAuthInstance().signOut();
    }

    private Boolean registerUserToDatabase(@NonNull FirebaseUser firebaseUser, String username) {
        String userId = firebaseUser.getUid();

        HashMap<String, String> userPair = new HashMap<>();
        userPair.put("id", userId);
        userPair.put("username", username);

        return mFirebaseRepo.getUserDatabaseReferenceInstance()
                .child(userId)
                .setValue(userPair)
                .isSuccessful();
    }
}
