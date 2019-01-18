package com.example.chiragpc.starspace.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.authentication.login.LoginActivity;
import com.example.chiragpc.starspace.authentication.register.RegisterActivity;
import com.example.chiragpc.starspace.ui.home.HomeActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 12/15/2018 at 14:22.
 * Project - StarSpace
 */
public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    MaterialButton register;
    MaterialButton signIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        register = findViewById(R.id.splash_register);
        signIn = findViewById(R.id.splash_sign_in);

        SplashPresenter presenter = new SplashPresenter();
        presenter.attachView(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
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

    @Override
    public void currentUserAcc(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class).putExtra(USER_ID, user.getUid())
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
}
