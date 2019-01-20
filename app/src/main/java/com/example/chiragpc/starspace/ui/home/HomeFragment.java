package com.example.chiragpc.starspace.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.UserAccount;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

/**
 * Created by Chirag on 1/17/2019 at 20:19.
 * Project - StarSpace
 */
public class HomeFragment extends Fragment implements HomeContract.View {

    private static HomeFragment sHomeFragment;

    private String mUserId;

    private RecyclerView mRecyclerView;

    public static HomeFragment newInstance(String userId) {
        if (sHomeFragment == null) {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            args.putString(USER_ID, userId);
            fragment.setArguments(args);
            sHomeFragment = fragment;
        }
        return sHomeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserId = getArguments().getString(USER_ID);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i(mUserId);

        HomePresenter presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.userAccount(mUserId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = rootView.findViewById(R.id.home_recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }
}
