package com.example.chiragpc.starspace.data;


import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                .child(userId)
                .setValue(userPair)
                .isSuccessful();
    }

    public void userAccountInstace(String userId, OnTaskCompletion.UserAccountInfo taskCompletion) {
        mFirebaseRepo.getUserDatabaseReferenceInstance()
                .child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount user = dataSnapshot.getValue(UserAccount.class);
                taskCompletion.onCurrentUserInfoSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                taskCompletion.onCurrentUserInfoFailure(databaseError.toString());
            }
        });
    }

    public void userRegisteredInfo(OnTaskCompletion.userRegisteredInfo taskCompletion) {
        mFirebaseRepo.getUserDatabaseReferenceInstance().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserAccount> userAccountsList = new ArrayList<>();
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    UserAccount userAccount = user.getValue(UserAccount.class);
                    userAccountsList.add(userAccount);
                }
                taskCompletion.onAllUserInfoSuccess(userAccountsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                taskCompletion.onAllUserInfoFailure(databaseError.toString());
            }
        });
    }
}
