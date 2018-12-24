package com.example.chiragpc.starspace.ui.home;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.example.chiragpc.starspace.model.UserAccount;
import com.example.chiragpc.starspace.ui.settings.SettingsFragment;
import com.example.chiragpc.starspace.ui.user.AllUsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    Toolbar mToolbar;

    BottomNavigationView mNavigation;

    //  Bottom Navigation View
    private BottomNavigationView.OnNavigationItemSelectedListener mItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    mToolbar.setTitle("Star Space");
                    return true;
                case R.id.navigation_friends:
                    Toast.makeText(HomeActivity.this, "Friends", Toast.LENGTH_SHORT).show();
                    mToolbar.setTitle("Friends");
                    return true;
                case R.id.navigation_users:
                    AllUsersFragment allUsers = new AllUsersFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, allUsers).commit();
                    mToolbar.setTitle("All Users");
                    return true;
                case R.id.navigation_settings:
                    SettingsFragment settings = new SettingsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settings).commit();
                    Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    mToolbar.setTitle("Settings");
                    return true;
            }
            return false;
        }
    };

    HomePresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewHolder();

        setSupportActionBar(mToolbar);

        Logger.addLogAdapter(new AndroidLogAdapter());
        String userId = "abc";
        if (getIntent().hasExtra("userid")) {
            userId = getIntent().getStringExtra("userid");
        }

        mNavigation.setOnNavigationItemSelectedListener(mItemSelectedListener);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mPresenter.userAccount(userId);
    }

    private void viewHolder() {
        mToolbar = findViewById(R.id.home_toolbar);

        mNavigation = findViewById(R.id.navigation_view);
    }

    @Override
    public void userAccountSuccess(UserAccount account) {
        mToolbar.setTitle(account.getUserName());
    }

    @Override
    public void userAccountFailure(String error) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
