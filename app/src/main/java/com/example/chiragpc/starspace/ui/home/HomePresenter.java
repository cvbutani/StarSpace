package com.example.chiragpc.starspace.ui.home;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.MessageTime;
import com.example.chiragpc.starspace.model.UserAccount;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Chirag on 12/18/2018 at 19:46.
 * Project - StarSpace
 */
public class HomePresenter extends BasePresenter<HomeContract.View>
        implements HomeContract.Presenter {

    private DataManager mDataManager;
    private UserAccount userAccount;

    public HomePresenter() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public HomeContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(HomeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void userAccount(String userId) {
        mDataManager.userAccountDataRepo(userId, new OnTaskCompletion.UserAccountInfo() {
            @Override
            public void onCurrentUserInfoSuccess(UserAccount account) {
                userAccount = account;
                if (account.getFriends() != null) {
                    for (String id : account.getFriends()) {
                        lastMessage(userId, id);
                    }
                }
            }

            @Override
            public void onCurrentUserInfoFailure(String error) {
            }
        });
    }

    @Override
    public void lastMessage(String senderId, String receiverId) {
        mDataManager.getMessagesDataRepo(senderId, receiverId, new OnTaskCompletion.GetMessages() {
            @Override
            public void onGetMessagesSuccess(List<MessageTime> messages) {

            }

            @Override
            public void onGetLastMessageSuccess(Map<String, MessageTime> userMessage) {
                getView().getLastMessage(userMessage);
            }

            @Override
            public void onGetMessagesFailure(String error) {

            }
        });
    }

}
