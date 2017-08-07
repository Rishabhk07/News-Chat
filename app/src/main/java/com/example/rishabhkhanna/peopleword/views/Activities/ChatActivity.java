package com.example.rishabhkhanna.peopleword.views.Activities;

import android.app.Dialog;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rishabhkhanna.peopleword.Adapters.ChatAdapter;
import com.example.rishabhkhanna.peopleword.Network.ChatAPI;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getChats;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Chat;
import com.example.rishabhkhanna.peopleword.model.ChatRoom;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.TimerTask;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    EditText etChat;
    Button btnSend;
    public static final String TAG = "ChatActivity";
    Socket socket = null;
    NewsJson thisJson;
    RecyclerView recyclerView;
    ArrayList<Chat> chatsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisJson = new Gson().fromJson(getIntent().getStringExtra(Constants.CHAT_KEY), NewsJson.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            socket = IO.socket("http://192.168.1.33:9999/");
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
        btnSend = (Button) findViewById(R.id.btnSend);
        recyclerView = (RecyclerView) findViewById(R.id.rvChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ChatAdapter chatAdapter = new ChatAdapter(chatsList, this);
        recyclerView.setAdapter(chatAdapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                if(socket.connected()){
                    if (AccessToken.getCurrentAccessToken() != null) {
                        AccessToken.getCurrentAccessToken().getUserId();
                        String chatMsg = etChat.getText().toString();
                        Chat chat = new Chat(chatMsg, thisJson.getMsid(), thisJson.getId(), AccessToken.getCurrentAccessToken().getUserId());
                        Log.d(TAG, "onClick: " + socket.emit("new_message", new Gson().toJson(chat)));
                    }
                }else{
                    Dialog dialog = new Dialog().
                    socket.connect();
                }


            }
        });
        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "call: " + data);
                Chat chat = null;
                try {
                    chat = new Chat(data.getString("message")
                            , data.getString("msid")
                            , Long.valueOf(data.getString("news_id"))
                            ,data.getString("from") );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                chatsList.add(chat);

                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        socket.on("from_server", onNewMessage);

        ChatAPI.getChatInstance()
                .retrofit
                .create(getChats.class)
                .getChat(thisJson.getId())
                .enqueue(new Callback<ArrayList<Chat>>() {
            @Override
            public void onResponse(Call<ArrayList<Chat>> call, Response<ArrayList<Chat>> response) {
                Log.d(TAG, "onResponse: chat message request : " + call.request() );
                Log.d(TAG, "onResponse: Chat Response : " + response.body());
                chatsList.addAll(response.body());
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Chat>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request());
            }
        });

    }

    @Override
    protected void onStop() {
        socket.emit("leave_group", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));
        super.onStop();
    }
}
