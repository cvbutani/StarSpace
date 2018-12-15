package com.example.chiragpc.starspace.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.chiragpc.starspace.HomeActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.login_username)
    EditText mEmail;

    @BindView(R.id.login_password)
    EditText mPassword;

    @BindView(R.id.login_progressBar)
    ProgressBar mProgressBar;

    LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
    }

    @OnClick(R.id.login_signin)
    public void signIn() {
        String email = mEmail.getText().toString().trim();
        String pass = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            mEmail.setError("Please enter email address");
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            mPassword.setError("Please enter password");
        }
        mPresenter.signInUser(email, pass);
    }

    @Override
    public void signInSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
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
