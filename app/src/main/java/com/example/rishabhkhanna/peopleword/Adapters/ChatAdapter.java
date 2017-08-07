package com.example.rishabhkhanna.peopleword.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Chat;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rishabhkhanna on 06/08/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    ArrayList<Chat> chatList;
    Context  context;
    public static final String TAG = "chatAdapter";
    public ChatAdapter(ArrayList chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(AccessToken.getCurrentAccessToken() != null){
            if(chatList.get(position).getUser_id().equals(AccessToken.getCurrentAccessToken().getUserId())){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(TAG, "onCreateViewHolder: ");
        int layout = R.layout.chat_message_right;
        if(viewType == 1){
            layout = R.layout.chat_message_left;
        }
        View view = li.inflate(layout,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.tvChatMessage.setText(chatList.get(position).getMessage());

        Picasso.with(context)
                .load(Profile.getCurrentProfile().getProfilePictureUri(100,100))
                .into(holder.imChatHead);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvChatMessage;
        de.hdodenhof.circleimageview.CircleImageView imChatHead;

        public ChatViewHolder(View itemView) {
            super(itemView);
            tvChatMessage = (TextView) itemView.findViewById(R.id.tvChatMessage);
            imChatHead = (CircleImageView) itemView.findViewById(R.id.imChatHead);
        }
    }
}
