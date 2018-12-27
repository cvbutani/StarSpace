package com.example.chiragpc.starspace.ui.friends;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static com.example.chiragpc.starspace.config.AppConfig.USER_ID;

public class FriendsFragment extends Fragment {

    private static FriendsFragment sFriendsFragment;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }
}
