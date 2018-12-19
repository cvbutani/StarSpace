package com.example.chiragpc.starspace.authentication.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chiragpc.starspace.ui.home.HomeActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Chirag on 12/15/2018 at 14:47.
 * Project - StarSpace
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    Toolbar mToolbar;

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;

    MaterialButton mSignUp;

    MaterialProgressBar mProgressBar;

    RegisterPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        viewHolder();

        setSupportActionBar(mToolbar);

        mPresenter = new RegisterPresenter();
        mPresenter.attachView(this);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.email_enter), Toast.LENGTH_SHORT).show();
                    mEmail.setError(getString(R.string.email_enter));
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.password_enter), Toast.LENGTH_SHORT).show();
                    mPassword.setError(getString(R.string.password_enter));
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegisterActivity.this, getString(R.string.username_enter), Toast.LENGTH_SHORT).show();
                    mUsername.setError(getString(R.string.username_enter));
                } else {
                    mPresenter.registerUser(username, email, password);
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewHolder() {
        mToolbar = findViewById(R.id.register_toolbar);

        mUsername = findViewById(R.id.register_display_name);
        mEmail = findViewById(R.id.register_email_address);
        mPassword = findViewById(R.id.register_password);

        mSignUp = findViewById(R.id.register_signup);

        mProgressBar = findViewById(R.id.register_progressBar);
    }

    @Override
    public void registerSuccess(String userId) {
        startActivity(new Intent(this, HomeActivity.class).putExtra("userid", userId)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void registerFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
