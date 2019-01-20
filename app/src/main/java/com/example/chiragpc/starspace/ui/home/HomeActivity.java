package com.example.chiragpc.starspace.ui.home;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.example.chiragpc.starspace.ui.friends.FriendsFragment;
import com.example.chiragpc.starspace.ui.settings.SettingsFragment;
import com.example.chiragpc.starspace.ui.user.AllUsersFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

public class HomeActivity extends BaseActivity {

//    Toolbar mToolbar;

    BottomNavigationView mNavigation;
    String userId = "";

    //  Bottom Navigation View
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment home = HomeFragment.newInstance(userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
                    return true;
                case R.id.navigation_friends:
                    FriendsFragment friends = FriendsFragment.newInstance(userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, friends).commit();
                    return true;
                case R.id.navigation_users:
                    AllUsersFragment allUsers = AllUsersFragment.newInstance(userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, allUsers).commit();
                    return true;
                case R.id.navigation_settings:
                    SettingsFragment settings = SettingsFragment.newInstance(userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settings).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewHolder();

        if (getIntent().hasExtra(USER_ID)) {
            userId = getIntent().getStringExtra(USER_ID);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(userId);
        if (savedInstanceState == null) {
            HomeFragment home = HomeFragment.newInstance(userId);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
        }
        mNavigation.setOnNavigationItemSelectedListener(mItemSelectedListener);

    }

    private void viewHolder() {
        mNavigation = findViewById(R.id.navigation_view);
    }
}
