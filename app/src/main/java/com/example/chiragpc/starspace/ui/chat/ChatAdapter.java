package com.example.chiragpc.starspace.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag on 1/8/2019 at 21:31.
 * Project - StarSpace
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<MessageTime> messageList;
    private Context mContext;

    ChatAdapter(List<MessageTime> messageList, Context mContext) {
        this.messageList = messageList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(mContext).inflate(R.layout.view_recycler_chat, parent, false);
        return new ViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageTime messages = messageList.get(position);
        holder.mImageView.setVisibility(View.GONE);
        if (messages != null && messages.getTextMessage() != null) {
            if (messages.getMessageType().equals("sentMessages")) {
                holder.mTextView.setGravity(Gravity.END);
            } else if (messages.getMessageType().equals("receivedMessages")) {
                holder.mTextView.setGravity(Gravity.START);
            }
            holder.mTextView.setText(messages.getTextMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView mTextView;
        AppCompatImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.view_message);
            mImageView = itemView.findViewById(R.id.view_image);
        }
    }
}