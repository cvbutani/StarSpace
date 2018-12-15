package com.example.chiragpc.starspace.data;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseRepo {

    public FirebaseRepo() {
    }

    FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }
}
