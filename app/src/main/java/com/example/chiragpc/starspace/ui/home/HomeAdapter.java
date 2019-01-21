package com.example.chiragpc.starspace.ui.home;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chiragpc.starspace.R;
import com.example.chiragpc.starspace.model.MessageTime;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Chirag on 1/20/2019 at 18:56.
 * Project - StarSpace
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context mContext;

    Map<String, MessageTime> mMessageSet;

    public HomeAdapter(Context mContext, Map<String, MessageTime> mMessageSet) {
        this.mContext = mContext;
        this.mMessageSet = mMessageSet;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View homeView = LayoutInflater.from(mContext).inflate(R.layout.view_recycler_home, parent, false);
        return new ViewHolder(homeView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.mUserName.setText(mMessageSet.keySet().iterator().next());
        holder.mUserMessage.setText(mMessageSet.get(mMessageSet.keySet().toArray()[0]).getTextMessage());

        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.i("Size ---------------" + mMessageSet.size());
    }

    @Override
    public int getItemCount() {
        return mMessageSet.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView mUserName, mUserMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserName = itemView.findViewById(R.id.view_user_name);
            mUserMessage = itemView.findViewById(R.id.view_user_message);
        }
    }
}
