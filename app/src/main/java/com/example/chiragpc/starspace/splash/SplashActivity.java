package com.example.chiragpc.starspace.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.authentication.LoginActivity;
import com.example.chiragpc.starspace.authentication.register.RegisterActivity;
import com.example.chiragpc.starspace.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Chirag on 12/15/2018 at 14:22.
 * Project - StarSpace
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.splash_register)
    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.splash_sign_in)
    public void signIn() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
