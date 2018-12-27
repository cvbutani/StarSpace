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

    private boolean isPressed = false;

    public interface OnItemClickListener {
        void onItemClick(String uId, boolean isFriend);
    }

    AllUserAdapter(List<UserAccount> mUsers, OnItemClickListener mOnClickListener, Context context) {
        this.mUsers = mUsers;
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

        if (!isPressed) {
            holder.mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.mFriendRequestButton.setText(mContext.getString(R.string.cancel_friend_request));
                    mOnClickListener.onItemClick(user.getId(), isPressed);
                    isPressed = true;
                }
            });
        } else {
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

    @Override
    public int getItemCount() {
        return mUsers.size();
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
