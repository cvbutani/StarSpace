package com.example.chiragpc.starspace;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private ActionBar mToolbar;

    private String mUsername;
    public static final String ANONYMOUS = "ANONYMOUS";

    @BindView(R.id.navigationView)
    public BottomNavigationView navigation;

    FirebaseAuth.AuthStateListener mAuthStateListener;

    FirebaseAuth mFirebaseAuth;

    FirebaseUser mCurrentUser;

//  Bottom Navigation View
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_albums:
                    mToolbar.setTitle("Songs");
                    return true;
                case R.id.navigation_artists:
                    Toast.makeText(HomeActivity.this, "Artists", Toast.LENGTH_SHORT).show();
                    mToolbar.setTitle("Artists");
                    return true;
                case R.id.navigation_songs:
                    Toast.makeText(HomeActivity.this, "Songs", Toast.LENGTH_SHORT).show();
                    mToolbar.setTitle("Songs");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        mToolbar = getSupportActionBar();
        navigation.setOnNavigationItemSelectedListener(mItemSelectedListener);
        mFirebaseAuth = FirebaseAuth.getInstance();

//        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                mCurrentUser = firebaseAuth.getCurrentUser();
//                if (mCurrentUser != null) {
//                    onSignedInInitialize(mCurrentUser);
//                } else {
//                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//                }
//            }
//        };
    }

    private void onSignedInInitialize(FirebaseUser user) {
        if (user != null) {
            Logger.i(user.getDisplayName());
        }
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mAuthStateListener != null) {
//            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
//        }
//    }

    //    private void attachDatabaseReadListener() {
//        if (mChildEventListener == null) {
//            mChildEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
//                    mMessageAdapter.add(friendlyMessage);
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            };
//            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
//        }
//    }
//
//    private void detachDatabaseReadListener() {
//        if (mChildEventListener != null) {
//            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
//            mChildEventListener = null;
//        }
//    }
}
