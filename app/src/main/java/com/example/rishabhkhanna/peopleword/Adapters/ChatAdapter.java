package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Chat;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 06/08/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    ArrayList<Chat> chatList;
    Context  context;

    public ChatAdapter(ArrayList chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.chat_message_left,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.tvChatMessage.setText(chatList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvChatMessage;
        public ChatViewHolder(View itemView) {
            super(itemView);
            tvChatMessage = (TextView) itemView.findViewById(R.id.tvChats);
        }
    }
}
