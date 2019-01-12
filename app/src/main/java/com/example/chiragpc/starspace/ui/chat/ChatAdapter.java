package com.example.chiragpc.starspace.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Chirag on 1/8/2019 at 21:31.
 * Project - StarSpace
 */
public class ChatAdapter extends ArrayAdapter<MessageTime> {

    public ChatAdapter(@NonNull Context context, int resource, @NonNull List<MessageTime> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_recycler_chat, parent, false);
        }

        AppCompatTextView chatMessages = convertView.findViewById(R.id.view_message);
        AppCompatImageView imageMessage = convertView.findViewById(R.id.view_image);

        MessageTime message = getItem(position);

//        boolean isPhoto = message.getImageUrl() != null;
//        if (isPhoto) {
//            chatMessages.setVisibility(View.GONE);
//            imageMessage.setVisibility(View.VISIBLE);
//            Picasso.get().load(message.getImageUrl()).into(imageMessage);
//        } else {
            imageMessage.setVisibility(View.GONE);
            chatMessages.setVisibility(View.VISIBLE);
            if (message != null && message.getTextMessage() != null) {
                if (message.getMessageType().equals("sentMessages")) {
                    chatMessages.setGravity(Gravity.END);
                } else if (message.getMessageType().equals("receivedMessages")) {
                    chatMessages.setGravity(Gravity.START);
                }
                chatMessages.setText(message.getTextMessage());
            }
//        }

        return convertView;
    }
}
