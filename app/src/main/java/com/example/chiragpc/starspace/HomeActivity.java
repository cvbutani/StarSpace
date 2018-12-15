package com.example.chiragpc.starspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chiragpc.starspace.authentication.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
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

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mCurrentUser = firebaseAuth.getCurrentUser();
                if (mCurrentUser != null) {
                    onSignedInInitialize(mCurrentUser);
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign In Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onSignedInInitialize(FirebaseUser user) {
        if (user != null) {
            Logger.i(user.getDisplayName());
        }
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

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
