package com.example.chiragpc.starspace.ui.settings;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;

/**
 * Created by Chirag on 12/17/2018 at 21:19.
 * Project - StarSpace
 */
public class SettingsPresenter
        extends BasePresenter<SettingsContract.View>
        implements SettingsContract.Presenter {

    private DataManager mDataManager;

    public SettingsPresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public SettingsContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(SettingsContract.View view) {
        super.attachView(view);
    }

    @Override
    public void logout() {
        getView().showProgressBar();
        mDataManager.signOutDataRepo();
        getView().hideProgressBar();
    }
}
