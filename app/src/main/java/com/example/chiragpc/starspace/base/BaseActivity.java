package com.example.chiragpc.starspace.base;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.chiragpc.starspace.data.DataManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private DataManager mDataManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
    }
}
