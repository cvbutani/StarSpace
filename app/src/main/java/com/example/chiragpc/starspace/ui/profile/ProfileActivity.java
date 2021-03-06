package com.example.chiragpc.starspace.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.base.BaseActivity;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.chiragpc.starspace.config.AppConfig.DECLINE_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 1/1/2019 at 16:42.
 * Project - StarSpace
 */
public class ProfileActivity
        extends BaseActivity
        implements ProfileContract.View {

    private AppCompatTextView mSignOut, mEdit, mUserName;

    private ProfilePresenter mPresenter;

    private MaterialProgressBar mProgressBar;

    private CircularImageView mUserProfilePic;

    private MaterialButton mSendFriendRequest, mCancelFriendRequest;

    private String mUserId, mSenderUId;

    private UserAccount mCurrentUserAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        if (getIntent() != null) {
            mUserId = getIntent().getStringExtra(USER_ID);
            mSenderUId = getIntent().getStringExtra("current_user");
        }

        viewHolder();

        mPresenter = new ProfilePresenter();
        mPresenter.attachView(this);
        mPresenter.authorizedUserAccount(mSenderUId);
        mPresenter.userAccount(mUserId);
    }

    private void viewHolder() {
        mEdit = findViewById(R.id.settings_edit);
        mSignOut = findViewById(R.id.settings_logout);
        mEdit.setVisibility(View.GONE);
        mSignOut.setVisibility(View.GONE);

        mUserName = findViewById(R.id.settings_username);
        mProgressBar = findViewById(R.id.settings_progressBar);
        mUserProfilePic = findViewById(R.id.settings_profile_pic);

        mCancelFriendRequest = findViewById(R.id.profile_cancel_request);
        mCancelFriendRequest.setVisibility(View.GONE);
        mSendFriendRequest = findViewById(R.id.profile_friend_request);
    }

    @Override
    public void getCurrentuser(UserAccount account) {
        mCurrentUserAccount = account;
        mUserName.setText(account.getUsername());
        if (account.getProfilePic() != null) {
            Picasso.get().load(account.getProfilePic()).into(mUserProfilePic);
        }
        cancelFriendRequest();
        if (account.getRequestSent() != null) {
            if (account.getRequestSent().contains(mSenderUId)) {
                mSendFriendRequest.setVisibility(View.GONE);
            }
        } else {
            sendFriendRequest();
        }
        if (account.getRequestReceived() != null) {
            for (String requestId : account.getRequestReceived()) {
                if (mSenderUId.equals(requestId)) {
                    mSendFriendRequest.setVisibility(View.GONE);
//                    mCancelFriendRequest.setText("DECLINE FRIEND REQUEST");
                }
            }
        } else {
            sendFriendRequest();
        }
    }

    private void sendFriendRequest() {
        mSendFriendRequest.setVisibility(View.VISIBLE);
        mSendFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.friendRequest(false, mSenderUId, mUserId);

            }
        });
    }

    private void cancelFriendRequest() {
        mCancelFriendRequest.setVisibility(View.VISIBLE);
        mCancelFriendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.friendRequestResponse(mUserId, mSenderUId, DECLINE_REQUEST);
            }
        });
    }

    @Override
    public void getAuthorizedUser(UserAccount account) {
    }

    @Override
    public void friendRequestStatus(boolean isSuccess) {
        if (isSuccess) {
            mSendFriendRequest.setVisibility(View.GONE);
            cancelFriendRequest();
        }

    }

    @Override
    public void getCurrentUserFailure(String error) {

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
