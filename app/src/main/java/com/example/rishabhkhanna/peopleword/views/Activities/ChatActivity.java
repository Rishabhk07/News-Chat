package com.example.rishabhkhanna.peopleword.views.Activities;

import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Chat;
import com.example.rishabhkhanna.peopleword.model.ChatRoom;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.facebook.AccessToken;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    EditText etChat;
    Button btnSend;
    public static final String TAG = "ChatActivity";
    Socket socket = null;
    NewsJson thisJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        thisJson = new Gson().fromJson(getIntent().getStringExtra(Constants.CHAT_KEY),NewsJson.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        etChat = (EditText) findViewById(R.id.etChat);
        btnSend = (Button) findViewById(R.id.btnSend);
        try {
            socket = IO.socket("http://192.168.1.33:9999/");
            Log.d(TAG, "onStart: IO.socket successfully");
        } catch (URISyntaxException e) {
            Log.d(TAG, "onStart: unable to connect");
            e.printStackTrace();
        }
        if(socket != null){
            Log.d(TAG, "onCreate: " + socket.connect());
            if (AccessToken.getCurrentAccessToken() != null)
            socket.emit("join_room",new Gson().toJson(new ChatRoom(thisJson.getMsid(),AccessToken.getCurrentAccessToken().getUserId(),thisJson.getId())));

        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");

                if(AccessToken.getCurrentAccessToken() != null){
                    AccessToken.getCurrentAccessToken().getUserId();
                    String chatMsg = etChat.getText().toString();
                    Chat chat = new Chat(chatMsg,thisJson.getMsid(),thisJson.getId(),AccessToken.getCurrentAccessToken().getUserId());
                    Log.d(TAG, "onClick: " + socket.emit("new_message",new Gson().toJson(chat)));
                }

            }
        });
        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                Log.d(TAG, "call: " + data);
            }
        };
        socket.on("from_server",onNewMessage);


    }

    @Override
    protected void onStop() {
        socket.emit("leave_group",new Gson().toJson(new ChatRoom(thisJson.getMsid(),AccessToken.getCurrentAccessToken().getUserId(),thisJson.getId())));
        super.onStop();
    }
}
