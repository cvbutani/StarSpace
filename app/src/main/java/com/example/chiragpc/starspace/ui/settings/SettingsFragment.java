package com.example.chiragpc.starspace.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.splash.SplashActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Chirag on 12/17/2018 at 21:04.
 * Project - StarSpace
 */
public class SettingsFragment extends Fragment implements SettingsContract.View, View.OnClickListener {

    LinearLayoutCompat mLinearLayout;

    SettingsPresenter mPresenter;

    MaterialProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new SettingsPresenter();
        mPresenter.attachView(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        mLinearLayout = rootView.findViewById(R.id.settings_logout);
        mProgressBar = rootView.findViewById(R.id.settings_progressBar);
        mLinearLayout.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void logoutSuccess() {
        mPresenter.logout();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_logout:
                Logger.i("Signing Out");
                mPresenter.logout();
                startActivity(new Intent(getContext(), SplashActivity.class));
                break;
        }
    }


}
