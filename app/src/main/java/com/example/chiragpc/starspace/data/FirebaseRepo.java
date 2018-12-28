package com.example.chiragpc.starspace.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.chiragpc.starspace.config.AppConfig.FIREBASE_NODE;

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

    CollectionReference getFriendRequestDatabaseReferenceInstance() {
        return FirebaseFirestore.getInstance().collection(FIREBASE_NODE);
    }

    StorageReference getProfileStorageReference() {
        return FirebaseStorage.getInstance().getReference().child("user_profile");
    }

}
