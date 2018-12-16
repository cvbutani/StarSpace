package com.example.chiragpc.starspace;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ActionBar mToolbar;

    BottomNavigationView mNavigation;

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

        viewHolder();

        Logger.addLogAdapter(new AndroidLogAdapter());

        mToolbar = getSupportActionBar();
        mNavigation.setOnNavigationItemSelectedListener(mItemSelectedListener);
    }

    private void viewHolder() {
        mNavigation = findViewById(R.id.navigation_view);
    }
}
