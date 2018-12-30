package com.example.chiragpc.starspace.ui.friends;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.chiragpc.starspace.config.AppConfig.ACCEPT_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.DECLINE_REQUEST;
import static com.example.chiragpc.starspace.config.AppConfig.UNFRIEND;
import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

public class FriendsFragment extends Fragment implements FriendsContract.View, FriendsAdapter.OnItemClickListener {

    private static FriendsFragment sFriendsFragment;

    private FriendsPresenter mPresenter;

    private FriendsAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private String mUserId;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(String userId) {
        if (sFriendsFragment == null) {
            FriendsFragment fragment = new FriendsFragment();
            Bundle args = new Bundle();
            args.putString(USER_ID, userId);
            fragment.setArguments(args);
            sFriendsFragment = fragment;
        }
        return sFriendsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(USER_ID);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(mUserId);

        mPresenter = new FriendsPresenter();
        mPresenter.attachView(this);
        mPresenter.getfriendRequest(mUserId);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

        mRecyclerView = rootView.findViewById(R.id.friends_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void friendRequestsReceived(List<UserAccount> userAccountList) {
        mAdapter = new FriendsAdapter(userAccountList, this, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void friendRequestFailure(String error) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onItemClick(String userId, int requestType) {
        switch(requestType) {
            case ACCEPT_REQUEST:
                mPresenter.friendRequestAccepted(mUserId, userId, true);
                break;
            case DECLINE_REQUEST:
                mPresenter.friendRequestAccepted(mUserId,userId,false);
                break;
            case UNFRIEND:

                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        sFriendsFragment = null;
    }

}
