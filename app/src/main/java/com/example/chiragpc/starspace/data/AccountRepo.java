package com.example.chiragpc.starspace.data;


import android.util.Log;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

class AccountRepo {

    private FirebaseRepo mFirebaseRepo;
    private UserAccount mUserAccount;

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
                            taskCompletion.onSuccess(task.getResult().getUser().getUid());
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
                                    taskCompletion.onSuccess(user.getUid());
                                }
                            }
                        } else {
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

    private boolean registerUserToDatabase(@NonNull FirebaseUser firebaseUser, String username) {
        String userId = firebaseUser.getUid();

        HashMap<String, String> userPair = new HashMap<>();
        userPair.put("id", userId);
        userPair.put("username", username);

        return mFirebaseRepo.getUserDatabaseReferenceInstance()
                .document()
                .set(userPair)
                .isSuccessful();
    }

    public void userAccountInstace(String userId, OnTaskCompletion.UserAccountInfo taskCompletion) {
        mFirebaseRepo.getUserDatabaseReferenceInstance()
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserAccount account = snapshot.toObject(UserAccount.class);
                                taskCompletion.onCurrentUserInfoSuccess(account);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskCompletion.onCurrentUserInfoFailure(e.getMessage());
            }
        });
    }

    public void userRegisteredInfo(OnTaskCompletion.userRegisteredInfo taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo.getUserDatabaseReferenceInstance()
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<UserAccount> userAccounts = new ArrayList<>();
                            QuerySnapshot snapshot = task.getResult();
                            for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
                                mFirebaseRepo.getUserDatabaseReferenceInstance()
                                        .document(documentSnapshot.getId()).get().getResult().getDocumentReference(documentSnapshot.getId());
                            }
                            taskCompletion.onAllUserInfoSuccess(userAccounts);
                        }
                    }
                });
    }

    private UserAccount getUserFromId(String id) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo.getUserDatabaseReferenceInstance().document(id).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot snapshot = task.getResult();
                        mUserAccount = snapshot.toObject(UserAccount.class);
                    }
                });
        Logger.i(mUserAccount.getUserName());
        return mUserAccount;
    }
}
