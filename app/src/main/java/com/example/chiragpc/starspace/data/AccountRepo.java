package com.example.chiragpc.starspace.data;


import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import androidx.annotation.NonNull;

public class AccountRepo {

    private FirebaseRepo mFirebaseRepo;

    public AccountRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    public void signInAccRepo(String email, String password, OnTaskCompletion taskCompletion) {
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

    public void registerAccRepo(String username, String email, String password, OnTaskCompletion taskCompletion) {
        mFirebaseRepo.getFirebaseAuthInstance()
                .createUserWithEmailAndPassword(email, password)
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

    public void resetPasswordAccRepo(String email, OnTaskCompletion.ResetPassword taskCompletion) {
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
}
