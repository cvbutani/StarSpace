package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;


import androidx.annotation.NonNull;

class AccountRepo {

    private FirebaseRepo mFirebaseRepo;

    AccountRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    void signInAccRepo(String email, String password, OnTaskCompletion taskCompletion) {
        mFirebaseRepo
                .getFirebaseAuthInstance()
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
        mFirebaseRepo
                .getFirebaseAuthInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mFirebaseRepo.getFirebaseAuthInstance().getCurrentUser();
                            if (user != null) {
                                registerUserToDatabase(user, username);
                                taskCompletion.onSuccess(user.getUid());
                            }
                        } else {
                            taskCompletion.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    void resetPasswordAccRepo(String email, OnTaskCompletion.ResetPassword taskCompletion) {
        mFirebaseRepo
                .getFirebaseAuthInstance()
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

    private void registerUserToDatabase(@NonNull FirebaseUser firebaseUser, String username) {
        String userId = firebaseUser.getUid();

        HashMap<String, String> userPair = new HashMap<>();
        userPair.put("id", userId);
        userPair.put("username", username);
        userPair.put("friendRequestReceived", null);
        userPair.put("friendRequestSent", null);

        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(userId)
                .set(userPair)
                .isSuccessful();
    }

    void userAccountInstace(String userId, OnTaskCompletion.UserAccountInfo taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
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

    void userRegisteredInfo(String userId, OnTaskCompletion.userRegisteredInfo taskCompletion) {
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            List<UserAccount> accountList = task.getResult().toObjects(UserAccount.class);
                            taskCompletion.onAllUserInfoSuccess(accountList);
                        }
                    }
                });
    }

}
