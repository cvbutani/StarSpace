package com.example.chiragpc.starspace.ui.chat;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.example.chiragpc.starspace.model.UserAccount;

import java.util.List;

/**
 * Created by Chirag on 1/8/2019 at 20:06.
 * Project - StarSpace
 */
public class ChatPresenter
        extends BasePresenter<ChatContract.View>
        implements ChatContract.Presenter {

    private DataManager mDataManager;

    public ChatPresenter() {
        this.mDataManager = DataManager.getInstance();
    }

    @Override
    public ChatContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(ChatContract.View view) {
        super.attachView(view);
    }

    @Override
    public void receiverUserAccountId(String userId) {
        mDataManager.userAccountDataRepo(userId, new OnTaskCompletion.UserAccountInfo() {
            @Override
            public void onCurrentUserInfoSuccess(UserAccount account) {
                getView().receiverUserDetailSuccess(account);
            }

            @Override
            public void onCurrentUserInfoFailure(String error) {
                getView().messageFailure(error);
            }
        });
    }

    @Override
    public void sendMessage(String message, String senderId, String receiverId) {
        mDataManager.saveMessagesDataRepo(message, senderId, receiverId);
    }

    @Override
    public void getMessage(String senderId, String receiverId) {
        mDataManager.getMessagesDataRepo(senderId, receiverId, new OnTaskCompletion.GetMessages() {
            @Override
            public void onGetMessagesSuccess(List<MessageTime> messages) {
                getView().getMessageSuccess(messages);
            }

            @Override
            public void onGetMessagesFailure(String error) {
                getView().messageFailure(error);
            }
        });
    }
}