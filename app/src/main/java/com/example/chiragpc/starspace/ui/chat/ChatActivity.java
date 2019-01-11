package com.example.chiragpc.starspace.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 1/6/2019 at 17:01.
 * Project - StarSpace
 */
public class ChatActivity extends BaseActivity implements ChatContract.View {

    AppCompatImageButton mSendButton;
    TextInputEditText mMessage;
    String mMessageSenderId, mMessegeReceiverId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewHolder();

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getIntent().hasExtra(USER_ID)) {
            mMessageSenderId = getIntent().getStringExtra(USER_ID);
        }
        if (getIntent().hasExtra("receiver_user_id")) {
            mMessegeReceiverId = getIntent().getStringExtra("receiver_user_id");
        }

        ChatPresenter mPresenter = new ChatPresenter();
        mPresenter.attachView(this);

        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MessageTime time = new MessageTime (mMessage.getText().toString(), System.currentTimeMillis());
//                List<MessageTime> messageTimes = new ArrayList<>();
//                messageTimes.add(time);
//                ChatMessage chatMessage = new ChatMessage(messageTimes, null, mMessageSenderId, mMessegeReceiverId);
                mPresenter.sendMessage(mMessage.getText().toString(), mMessageSenderId, mMessegeReceiverId);
                mMessage.setText("");
            }
        });

    }

    private void viewHolder() {
        mSendButton = findViewById(R.id.chat_send);
        mMessage = findViewById(R.id.chat_message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void sendingMessageSuccess(boolean isSuccess) {

    }

    @Override
    public void sendingMessageFailure(String error) {

    }
}
