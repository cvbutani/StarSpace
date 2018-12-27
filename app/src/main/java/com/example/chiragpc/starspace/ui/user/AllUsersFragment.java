package com.example.chiragpc.starspace.ui.user;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 12/23/2018 at 11:03.
 * Project - StarSpace
 */
public class AllUsersFragment extends Fragment implements AllUsersContract.View, AllUserAdapter.OnItemClickListener {

    private AllUsersPresenter mPresenter;

    private RecyclerView mRecyclerView;

    private MaterialProgressBar mProgressBar;

    private AllUserAdapter mAdapter;

    String senderUserId;

    public AllUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AllUsersPresenter();
        mPresenter.attachView(this);
        if (!this.getArguments().isEmpty()) {
            senderUserId = this.getArguments().getString(USER_ID);
        }
        mPresenter.allUser(senderUserId);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_users, container, false);

        mRecyclerView = rootView.findViewById(R.id.all_user_recycler_view);
        mProgressBar = rootView.findViewById(R.id.all_user_progressBar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void allUserSuccess(List<UserAccount> userAccountList) {
        mAdapter = new AllUserAdapter(userAccountList, this, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void friendRequestStatus(boolean isSuccess) {
        Logger.i("Success or Failure - " + isSuccess);
    }

    @Override
    public void allUserFailure(String error) {
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void onItemClick(String receiverUserId, boolean isFriend) {

        // isFriend = true means that they are friend.
        // isFriend = false means that they are not friend.

        if (isFriend) {

        } else {
            mPresenter.sendFriendRequest(isFriend, senderUserId, receiverUserId);
        }
    }
}
