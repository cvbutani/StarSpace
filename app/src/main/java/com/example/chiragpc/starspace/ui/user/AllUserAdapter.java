package com.example.chiragpc.starspace.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.material.button.MaterialButton;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag on 12/23/2018 at 17:17.
 * Project - StarSpace
 */
public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ViewHolder> {

    private List<UserAccount> mUsers;

    private OnItemClickListener mOnClickListener;

    private Context mContext;

    private boolean isPressed;

    private String mCurrentUser;

    public interface OnItemClickListener {
        void onItemClick(String uId, boolean isFriend);

        void onProfileClick(String id);
    }

    AllUserAdapter(String user, List<UserAccount> mUsers, OnItemClickListener mOnClickListener, Context context) {
        this.mUsers = mUsers;
        this.mCurrentUser = user;
        this.mOnClickListener = mOnClickListener;
        this.mContext = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @NonNull
    @Override
    public AllUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(mContext).inflate(R.layout.view_recycler_all_user, parent, false);
        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserAdapter.ViewHolder holder, int position) {
        UserAccount user = mUsers.get(position);
        holder.mUsername.setText(user.getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onProfileClick(user.getId());
            }
        });

        holder.mFriendRequestButton.setVisibility(View.VISIBLE);
        holder.mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mFriendRequestButton.getText().equals(mContext.getString(R.string.send_friend_request))) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.cancel_friend_request));
                    mOnClickListener.onItemClick(user.getId(), false);
                    isPressed = true;
                } else if (holder.mFriendRequestButton.getText().equals(mContext.getString(R.string.cancel_friend_request))){
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.send_friend_request));
                    mOnClickListener.onItemClick(user.getId(), true);
                    isPressed = false;
                }
            }
        });
//
//            holder.mFriendRequestButton.setVisibility(View.VISIBLE);
//            holder.mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.mFriendRequestButton.setText(mContext.getString(R.string.send_friend_request));
//                    mOnClickListener.onItemClick(user.getId(), isPressed);
//                    isPressed = false;
//                }
//            });

        if (user.getRequestSent() != null) {
            for (String requests : user.getRequestSent()) {
                if (mCurrentUser.equals(requests)) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.accept));
                }
            }
        }
        if (user.getRequestReceived() != null) {
            for (String requsets : user.getRequestReceived()) {
                if (mCurrentUser.equals(requsets)) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.cancel_friend_request));
                }
            }
        }
        if (user.getFriends() != null) {
            for (String friends : user.getFriends()) {
                if (mCurrentUser.equals(friends)) {
                    holder.mFriendRequestButton.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    private void buttonAction(AllUserAdapter.ViewHolder holder, UserAccount user) {
        if (!isPressed) {
            holder.mFriendRequestButton.setVisibility(View.VISIBLE);
            holder.mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.cancel_friend_request));
                    mOnClickListener.onItemClick(user.getId(), isPressed);
                    isPressed = true;
                }
            });
        } else {
            holder.mFriendRequestButton.setVisibility(View.VISIBLE);
            holder.mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.send_friend_request));
                    mOnClickListener.onItemClick(user.getId(), isPressed);
                    isPressed = false;
                }
            });
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mUserProfilePic;
        AppCompatTextView mUsername;
        MaterialButton mFriendRequestButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserProfilePic = itemView.findViewById(R.id.view_user_profile_pic);
            mUsername = itemView.findViewById(R.id.view_user_name);
            mFriendRequestButton = itemView.findViewById(R.id.view_user_friend_request);
        }
    }
}
