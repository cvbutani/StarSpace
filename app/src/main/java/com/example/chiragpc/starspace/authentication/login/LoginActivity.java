package com.example.chiragpc.starspace.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chiragpc.starspace.HomeActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    EditText mEmail;
    EditText mPassword;

    ProgressBar mProgressBar;

    MaterialButton mSignIn;

    LoginPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewHolder();

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.email_enter), Toast.LENGTH_SHORT).show();
                    mEmail.setError(getString(R.string.email_enter));
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.password_enter), Toast.LENGTH_SHORT).show();
                    mPassword.setError(getString(R.string.password_enter));
                } else {
                    mPresenter.signInUser(email, pass);
                }
            }
        });
    }

    private void viewHolder() {
        mEmail = findViewById(R.id.login_email_address);
        mPassword = findViewById(R.id.login_password);

        mProgressBar = findViewById(R.id.login_progressBar);

        mSignIn = findViewById(R.id.login_signin);
    }

    @Override
    public void signInSuccess() {
        startActivity(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void signInError(String error) {
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
