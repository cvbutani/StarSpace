package com.example.chiragpc.starspace.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseRepo {

    FirebaseRepo() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
    }

    FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    CollectionReference getUserDatabaseReferenceInstance() {
        return FirebaseFirestore.getInstance().collection("user");
    }

}
