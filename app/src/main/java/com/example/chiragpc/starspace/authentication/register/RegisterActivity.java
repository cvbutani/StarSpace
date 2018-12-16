package com.example.chiragpc.starspace.authentication.register;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Chirag on 12/15/2018 at 14:47.
 * Project - StarSpace
 */
public class RegisterActivity extends BaseActivity {

    EditText mUsername;
    EditText mEmail;
    EditText mPassword;

    MaterialButton mSignUp;

    MaterialProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void viewHolder() {
        mUsername = findViewById(R.id.register_display_name);
        mEmail = findViewById(R.id.register_email_address);
        mPassword = findViewById(R.id.register_password);

        mSignUp = findViewById(R.id.register_signup);

        mProgressBar = findViewById(R.id.register_progressBar);
    }
}
