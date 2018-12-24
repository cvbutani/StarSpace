package com.example.chiragpc.starspace.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * Created by Chirag on 12/23/2018 at 11:03.
 * Project - StarSpace
 */
public class AllUsersFragment extends Fragment implements AllUsersContract.View, AllUserAdapter.OnItemClickListener {

    private AllUsersPresenter mPresenter;

    private RecyclerView mRecyclerView;

    private MaterialProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new AllUsersPresenter();
        mPresenter.attachView(this);
        mPresenter.allUser();

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

        AllUserAdapter adapter = new AllUserAdapter(userAccountList, this, getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void allUserFailure(String error) {
        Logger.i(error);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.i("Something Went wrong - " + position);
        Toast.makeText(getContext(), "Position - " + position , Toast.LENGTH_SHORT).show();
    }
}
