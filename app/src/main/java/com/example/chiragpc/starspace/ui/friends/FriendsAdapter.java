package com.example.chiragpc.starspace.ui.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.example.chiragpc.starspace.ui.user.AllUserAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag on 12/26/2018 at 17:37.
 * Project - StarSpace
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private List<UserAccount> mUser;

    private FriendsAdapter.OnItemClickListener mOnClickListener;

    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public FriendsAdapter(List<UserAccount> mUser, FriendsAdapter.OnItemClickListener mOnClickListener, Context mContext) {
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
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mUserProfilePic;
        AppCompatTextView mUserName, mUserStatus;
        MaterialButton mAccpetRequest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.view_user_name);
            mUserStatus = itemView.findViewById(R.id.view_user_status);
            mUserProfilePic = itemView.findViewById(R.id.view_user_profile_pic);
            mAccpetRequest = itemView.findViewById(R.id.view_request_accept);

        }
    }
}
