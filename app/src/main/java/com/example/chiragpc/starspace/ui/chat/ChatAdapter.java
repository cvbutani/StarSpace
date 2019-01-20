package com.example.chiragpc.starspace.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag on 1/8/2019 at 21:31.
 * Project - StarSpace
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<MessageTime> messageList;
    private Context mContext;
    private String mUserId;

    ChatAdapter(List<MessageTime> messageList, Context mContext, String userId) {
        this.messageList = messageList;
        this.mContext = mContext;
        this.mUserId = userId;
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
        holder.mSendImage.setVisibility(View.GONE);
        holder.mReceiveImage.setVisibility(View.GONE);

        if (messages != null && messages.getTextMessage() != null) {
            if (!messages.getSenderId().equals(mUserId)) {
                holder.mSenderLayout.setVisibility(View.VISIBLE);
                holder.mSendText.setText(messages.getTextMessage());
                holder.mReceiverLayout.setVisibility(View.GONE);
                holder.mReceiveTextTime.setVisibility(View.GONE);
                holder.mSendTextTime.setVisibility(View.VISIBLE);
                holder.mSendTextTime.setText(getFormattedDate(messages.getTimestamp()));
            } else {
                holder.mReceiverLayout.setVisibility(View.VISIBLE);
                holder.mReceiveText.setText(messages.getTextMessage());
                holder.mSenderLayout.setVisibility(View.GONE);
                holder.mSendTextTime.setVisibility(View.GONE);
                holder.mReceiveTextTime.setVisibility(View.VISIBLE);
                holder.mReceiveTextTime.setText(getFormattedDate(messages.getTimestamp()));
            }
        }

    }

    String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView mSendText, mSendTextTime, mReceiveText, mReceiveTextTime;
        AppCompatImageView mSendImage, mReceiveImage;
        LinearLayoutCompat mSenderLayout, mReceiverLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSenderLayout = itemView.findViewById(R.id.sender_layout);
            mSendText = itemView.findViewById(R.id.sender_message);
            mSendTextTime = itemView.findViewById(R.id.sender_time);
            mSendImage = itemView.findViewById(R.id.sender_image);

            mReceiverLayout = itemView.findViewById(R.id.received_layout);
            mReceiveText = itemView.findViewById(R.id.received_message);
            mReceiveTextTime = itemView.findViewById(R.id.received_time);
            mReceiveImage = itemView.findViewById(R.id.received_image);
        }
    }
}
