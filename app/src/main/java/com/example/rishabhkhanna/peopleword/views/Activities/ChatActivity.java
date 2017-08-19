package com.example.rishabhkhanna.peopleword.views.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rishabhkhanna.peopleword.Adapters.ChatAdapter;
import com.example.rishabhkhanna.peopleword.Network.ChatAPI;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getChats;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Chat;
import com.example.rishabhkhanna.peopleword.model.ChatRoom;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    EditText etChat;
    FloatingActionButton btnSend;
    ImageView userImageView;
    public static final String TAG = "ChatActivity";
    Socket socket = null;
    NewsJson thisJson;
    RecyclerView recyclerView;
    ArrayList<Chat> chatsList = new ArrayList<>();
    ChatAdapter chatAdapter;
    boolean anonym_user = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisJson = new Gson().fromJson(getIntent().getStringExtra(Constants.CHAT_KEY), NewsJson.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        try {
            socket = IO.socket("http://192.168.1.39:9999/");
            Log.d(TAG, "onStart: IO.socket successfully");
        } catch (URISyntaxException e) {
            Log.d(TAG, "onStart: unable to connect");
            e.printStackTrace();
        }
        if (socket != null) {
            Log.d(TAG, "onCreate: " + socket.connect());
            if (AccessToken.getCurrentAccessToken() != null)
                socket.emit("join_room", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));

        }
        etChat = (EditText) findViewById(R.id.etChat);
        btnSend = (FloatingActionButton) findViewById(R.id.btnSend);
        recyclerView = (RecyclerView) findViewById(R.id.rvChat);
        userImageView = (ImageView) findViewById(R.id.imSelectUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getChats();
        chatAdapter = new ChatAdapter(chatsList, this);
        recyclerView.setAdapter(chatAdapter);
        Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(100,100)).into(userImageView);

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ChatActivity.this, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                popupMenu.getMenu().add(1,1,1,Profile.getCurrentProfile().getName());
                popupMenu.getMenu().add(1,2,2,"Anonymous user");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case 1:
                                Picasso.with(ChatActivity.this)
                                        .load(Profile.getCurrentProfile().getProfilePictureUri(100,100))
                                        .into(userImageView);
                                anonym_user = false;
                                break;
                            case 2:
                                Picasso.with(ChatActivity.this)
                                        .load(R.drawable.person_placeholder)
                                        .into(userImageView);
                                anonym_user = true;
                                Log.d(TAG, "onMenuItemClick: on Anonymous user view");
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (socket.connected()) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        AccessToken.getCurrentAccessToken().getUserId();
                        String chatMsg = etChat.getText().toString();
                        Log.d(TAG, "onClick: " + chatMsg);
                        if(chatMsg.isEmpty()){
                            Log.d(TAG, "onClick: in chat isEmplty");
                            Toast.makeText(ChatActivity.this, "Message is Empty", Toast.LENGTH_SHORT).show();
                        }else {

                                Chat chat = new Chat(chatMsg, thisJson.getMsid(),
                                        thisJson.getId(), AccessToken.getCurrentAccessToken().getUserId(),anonym_user);

                            Log.d(TAG, "onClick: " + socket.emit("new_message", new Gson().toJson(chat)));
                            etChat.setText("");

                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage("You are not connected to chat").setPositiveButton("Connect again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            socket.connect();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            socket.connect();
                            getChats();
                        }
                    }).show();

                }


            }
        });
        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "call: " + data);
                Chat chat = new Gson().fromJson(String.valueOf(data),Chat.class);

                chatsList.add(chat);

                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatsList.size() - 1);
                    }
                });
            }
        };
        socket.on("from_server", onNewMessage);

    }

    @Override
    protected void onStop() {
        socket.emit("leave_group", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));
        socket.disconnect();
        super.onStop();
    }

    public void getChats() {
        ChatAPI.getChatInstance()
                .retrofit
                .create(getChats.class)
                .getChat(thisJson.getId(),thisJson.getMsid())
                .enqueue(new Callback<ArrayList<Chat>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Chat>> call, Response<ArrayList<Chat>> response) {
                        Log.d(TAG, "onResponse: chat message request : " + call.request());
                        Log.d(TAG, "onResponse: Chat Response : " + response.body());
                        chatsList.clear();
                        chatsList.addAll(response.body());
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(chatsList.size() - 1);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Chat>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
    }

}

