package me.rishabhkhanna.newschat.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.rishabhkhanna.newschat.Network.GetProfilePicture;
import me.rishabhkhanna.newschat.Network.interfaces.getProfilePic;
import me.rishabhkhanna.newschat.model.Chat;
import me.rishabhkhanna.newschat.model.FBProfilePicture;

import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Log.d(TAG, "getItemViewType: " + chatList.get(position).getUser_id());
            if(chatList.get(position).getUser_id().equals(AccessToken.getCurrentAccessToken().getUserId())){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layout = me.rishabhkhanna.newschat.R.layout.chat_message_right;
        if(viewType == 1){
            layout = me.rishabhkhanna.newschat.R.layout.chat_message_left;
        }
        View view = li.inflate(layout,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, int position) {
        holder.tvChatMessage.setText(chatList.get(position).getMessage());
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... params) {

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();
        if(chatList.get(position).getAnonym_user()){
            Picasso.with(context)
                    .load(me.rishabhkhanna.newschat.R.drawable.person_placeholder)
                    .into(holder.imChatHead);
        }else{
            GetProfilePicture.getInstance().retrofit.create(getProfilePic.class)
                    .getProfilePhoto(chatList.get(position).getUser_id(),"large",false)
                    .enqueue(new Callback<FBProfilePicture>() {
                        @Override
                        public void onResponse(Call<FBProfilePicture> call, Response<FBProfilePicture> response) {
//                            Log.d(TAG, "onResponse: " + call.request());
//                            Log.d(TAG, "onResponse: " + response.body());
                            Picasso.with(context)
                                    .load(response.body().getData().getUrl())
                                    .placeholder(me.rishabhkhanna.newschat.R.drawable.person_placeholder)
                                    .into(holder.imChatHead);
                        }
                        @Override
                        public void onFailure(Call<FBProfilePicture> call, Throwable t) {
//                            Log.d(TAG, "onFailure: " + call.request());
                        }
                    });
        }


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
            tvChatMessage = (TextView) itemView.findViewById(me.rishabhkhanna.newschat.R.id.tvChatMessage);
            imChatHead = (CircleImageView) itemView.findViewById(me.rishabhkhanna.newschat.R.id.imChatHead);
        }
    }
}
