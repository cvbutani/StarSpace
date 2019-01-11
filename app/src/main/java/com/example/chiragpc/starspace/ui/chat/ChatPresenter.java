package com.example.chiragpc.starspace.ui.chat;

import com.example.chiragpc.starspace.base.BasePresenter;
import com.example.chiragpc.starspace.data.DataManager;
import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;

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
    public void sendMessage(String message, String senderId, String receiverId) {
        mDataManager.saveMessagesDataRepo(message, senderId, receiverId, new OnTaskCompletion.SaveMessages() {
            @Override
            public void onSaveMessageSuccess(boolean isSuccess) {
                getView().sendingMessageSuccess(isSuccess);
            }

            @Override
            public void onSaveMessageFailure(String error) {
                getView().sendingMessageFailure(error);
            }
        });
    }
}
