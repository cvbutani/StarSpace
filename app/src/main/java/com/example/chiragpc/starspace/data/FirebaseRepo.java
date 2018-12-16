package com.example.chiragpc.starspace.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRepo {

    public FirebaseRepo() {
    }

    FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    DatabaseReference getUserDatabaseReferenceInstance() {
        return FirebaseDatabase.getInstance().getReference("user");
    }

}
