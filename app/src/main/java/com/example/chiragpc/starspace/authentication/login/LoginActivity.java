package com.example.chiragpc.starspace.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chiragpc.starspace.HomeActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    EditText mEmail;
    EditText mPassword;

    TextView mPasswordReset;

    MaterialProgressBar mProgressBar;

    MaterialButton mSignIn;

    LoginPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewHolder();

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        mSignIn.setOnClickListener(v -> {
            if (mSignIn.getText().equals(getString(R.string.common_signin_button_text))) {
                mPassword.setVisibility(View.VISIBLE);
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    showToast(getString(R.string.email_enter));
                    mEmail.setError(getString(R.string.email_enter));
                } else if (TextUtils.isEmpty(pass)) {
                    showToast(getString(R.string.password_enter));
                    mPassword.setError(getString(R.string.password_enter));
                } else {
                    mPresenter.signInUser(email, pass);
                }
            } else {
                String email = mEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    showToast(getString(R.string.email_enter));
                    mEmail.setError(getString(R.string.email_enter));
                } else {
                    showToast(getString(R.string.password_reset));
                    mPresenter.resetPassword(email);
                }
            }
        });

        mPasswordReset.setOnClickListener(v -> {
            mPassword.setVisibility(View.GONE);
            mSignIn.setText(getString(R.string.send));
        });
    }

    private void viewHolder() {
        mEmail = findViewById(R.id.login_email_address);
        mPassword = findViewById(R.id.login_password);

        mPasswordReset = findViewById(R.id.login_password_reset);

        mProgressBar = findViewById(R.id.login_progressBar);

        mSignIn = findViewById(R.id.login_signin);
    }

    @Override
    public void signInSuccess() {
        startActivity(new Intent(this, HomeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void resetPasswordSuccess() {
        mPassword.setVisibility(View.VISIBLE);
        mSignIn.setText(getString(R.string.common_signin_button_text));
    }

    @Override
    public void resetPasswordFailure(String error) {
        showToast(getString(R.string.reset_password_fail));
    }

    @Override
    public void signInError(String error) {
        showToast(error);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
