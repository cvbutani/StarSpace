package com.example.chiragpc.starspace.ui.home;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.example.chiragpc.starspace.model.UserAccount;
import com.example.chiragpc.starspace.ui.friends.FriendsFragment;
import com.example.chiragpc.starspace.ui.settings.SettingsFragment;
import com.example.chiragpc.starspace.ui.user.AllUsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

public class HomeActivity extends BaseActivity implements HomeContract.View {

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
//                    mToolbar.setTitle("Star Space");
                    return true;
                case R.id.navigation_friends:
                    FriendsFragment friends = FriendsFragment.newInstance(userId);
                    Logger.i("FRIENDS -------- " + userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, friends).commit();
//                    mToolbar.setTitle("Friends");
                    return true;
                case R.id.navigation_users:
                    AllUsersFragment allUsers = new AllUsersFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(USER_ID, userId);
                    allUsers.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, allUsers).commit();
//                    mToolbar.setTitle("All Users");
                    return true;
                case R.id.navigation_settings:
//                    mToolbar.setTitle("Settings");
                    SettingsFragment settings = SettingsFragment.newInstance(userId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, settings).commit();
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

//        setSupportActionBar(mToolbar);

        if (getIntent().hasExtra(USER_ID)) {
            userId = getIntent().getStringExtra(USER_ID);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(userId);

        mNavigation.setOnNavigationItemSelectedListener(mItemSelectedListener);

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        mPresenter.userAccount(userId);
    }

    private void viewHolder() {
//        mToolbar = findViewById(R.id.home_toolbar);

        mNavigation = findViewById(R.id.navigation_view);
    }

    @Override
    public void userAccountSuccess(UserAccount account) {
//        mToolbar.setTitle(account.getUsername());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
