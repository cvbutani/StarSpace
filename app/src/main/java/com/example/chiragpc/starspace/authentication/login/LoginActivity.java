package com.example.chiragpc.starspace.authentication.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.chiragpc.starspace.ui.home.HomeActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    TextInputEditText mEmail;
    TextInputEditText mPassword;

    AppCompatTextView mPasswordReset, mLoginTitle;

    MaterialProgressBar mProgressBar;

    MaterialButton mSignIn;

    LoginPresenter mPresenter;

    Toolbar mToolBar;

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
            mLoginTitle.setText("Reset Password");
            mPassword.setVisibility(View.GONE);
            mSignIn.setText(getString(R.string.send));
        });

//        Typeface googleSans = Typeface.createFromAsset(getApplicationContext().getAssets(), "googlesans_regular.ttf");
//        mSignIn.setTypeface(googleSans);
    }

    private void viewHolder() {

        mEmail = findViewById(R.id.login_email_address);
        mPassword = findViewById(R.id.login_password);

        mPasswordReset = findViewById(R.id.login_password_reset);
        mLoginTitle = findViewById(R.id.login_title);
        mProgressBar = findViewById(R.id.login_progressBar);

        mSignIn = findViewById(R.id.login_signin);
    }

    @Override
    public void signInSuccess(String userID) {
        startActivity(new Intent(this, HomeActivity.class).putExtra(USER_ID, userID)
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
       Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
    }
}
