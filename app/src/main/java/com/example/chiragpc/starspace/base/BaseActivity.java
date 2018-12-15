package com.example.chiragpc.starspace.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chiragpc.starspace.data.DataManager;

public class BaseActivity extends AppCompatActivity {

    private DataManager mDataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
    }
}
