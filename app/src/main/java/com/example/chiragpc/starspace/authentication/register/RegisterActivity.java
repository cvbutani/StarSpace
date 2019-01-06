package com.example.chiragpc.starspace.authentication.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.chiragpc.starspace.authentication.login.LoginActivity;
import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Chirag on 12/15/2018 at 14:47.
 * Project - StarSpace
 */
public class RegisterActivity
        extends BaseActivity
        implements RegisterContract.View {

    Toolbar mToolbar;

    TextInputEditText mUsername;
    TextInputEditText mEmail;
    TextInputEditText mPassword;

    AppCompatImageView mDatePicker;

    MaterialButton mSignUp;

    TextInputEditText mDatePicked;

    MaterialProgressBar mProgressBar;

    RegisterPresenter mPresenter;

    private int mYear, mMonth, mDay, mHour, mMinute;

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
                    Snackbar.make(v, "Registering", Snackbar.LENGTH_SHORT).show();
                    mPresenter.registerUser(username, email, password);
                }
            }
        });

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                mDatePicked.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void viewHolder() {
        mToolbar = findViewById(R.id.register_toolbar);

        mDatePicker = findViewById(R.id.register_date_picker);
        mDatePicked = findViewById(R.id.register_date_picked);
        mUsername = findViewById(R.id.register_display_name);
        mEmail = findViewById(R.id.register_email_address);
        mPassword = findViewById(R.id.register_password);

        mSignUp = findViewById(R.id.register_signup);

        mProgressBar = findViewById(R.id.register_progressBar);
    }

    @Override
    public void registerNewUserSuccess(String userId) {
        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, LoginActivity.class)
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
