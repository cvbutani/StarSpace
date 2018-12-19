package com.example.chiragpc.starspace.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.authentication.login.LoginActivity;
import com.example.chiragpc.starspace.authentication.register.RegisterActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Chirag on 12/15/2018 at 14:22.
 * Project - StarSpace
 */
public class SplashActivity extends AppCompatActivity {

    Toolbar mToolbar;

    MaterialButton register;
    MaterialButton signIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mToolbar = findViewById(R.id.splash_toolbar);
        setSupportActionBar(mToolbar);

        register = findViewById(R.id.splash_register);
        signIn = findViewById(R.id.splash_sign_in);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
    }
}
