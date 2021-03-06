package com.example.chiragpc.starspace.ui.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.chiragpc.starspace.config.AppConfig.ACCEPT_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.DECLINE_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.FRIEND_STATUS;
import static com.example.chiragpc.starspace.config.AppConfig.UNFRIEND;

/**
 * Created by Chirag on 12/26/2018 at 17:37.
 * Project - StarSpace
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private List<UserAccount> mUser;

    private FriendsAdapter.OnItemClickListener mOnClickListener;

    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(String userId, int requestType);
    }

    FriendsAdapter(List<UserAccount> mUser, FriendsAdapter.OnItemClickListener mOnClickListener, Context mContext) {
        this.mUser = mUser;
        this.mOnClickListener = mOnClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View friendsView = LayoutInflater.from(mContext).inflate(R.layout.view_recycler_friends, parent, false);
        return new ViewHolder(friendsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAccount userAccount = mUser.get(position);
        holder.mUserName.setText(userAccount.getUsername());
        if (userAccount.getType() != null && userAccount.getType().equals(FRIEND_STATUS)) {
            holder.mAccepetRequest.setVisibility(View.GONE);

            //  UNFRIEND USER
            holder.mCancelRequest.setText(mContext.getString(R.string.unfriend));
            holder.mCancelRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(userAccount.getId(), UNFRIEND);
                }
            });
            holder.mChatButton.setVisibility(View.VISIBLE);
            holder.mChatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(userAccount.getId(), 100);
                }
            });

        } else {
            holder.mAccepetRequest.setVisibility(View.VISIBLE);

            //  ACCEPT FRIEND REQUEST
            holder.mAccepetRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(userAccount.getId(), ACCEPT_REQUEST);
                }
            });

            //  DECLINE FRIEND REQUEST
            holder.mCancelRequest.setText(mContext.getString(R.string.decline));
            holder.mChatButton.setVisibility(View.GONE);
            holder.mCancelRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick(userAccount.getId(), DECLINE_REQUEST);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mUserProfilePic;
        AppCompatTextView mUserName, mUserStatus;
        MaterialButton mAccepetRequest, mCancelRequest;
        AppCompatImageButton mChatButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.view_user_name);
//            mUserStatus = itemView.findViewById(R.id.view_user_status);
            mUserProfilePic = itemView.findViewById(R.id.view_user_profile_pic);
            mAccepetRequest = itemView.findViewById(R.id.view_request_accept);
            mCancelRequest = itemView.findViewById(R.id.view_request_decline);
            mChatButton = itemView.findViewById(R.id.view_chat);
        }
    }
}
