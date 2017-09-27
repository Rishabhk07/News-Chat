package me.rishabhkhanna.peopleword.views.Activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.rishabhkhanna.peopleword.Adapters.ChatAdapter;
import me.rishabhkhanna.peopleword.Network.ChatAPI;
import me.rishabhkhanna.peopleword.Network.interfaces.getChats;
import me.rishabhkhanna.peopleword.R;
import me.rishabhkhanna.peopleword.model.Chat;
import me.rishabhkhanna.peopleword.model.ChatRoom;
import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.Constants;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
    FloatingActionButton btnSend;
    ImageView userImageView;
    TextView tvHeading;
    TextView tvDetail;
    ImageView ivClear;
    CardView cardViewNews;
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
        setContentView(me.rishabhkhanna.peopleword.R.layout.activity_chat);
        socket = ChatAPI.getSocket();
        setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        etChat = (EditText) findViewById(me.rishabhkhanna.peopleword.R.id.etChat);
        btnSend = (FloatingActionButton) findViewById(me.rishabhkhanna.peopleword.R.id.btnSend);
        recyclerView = (RecyclerView) findViewById(me.rishabhkhanna.peopleword.R.id.rvChat);
        userImageView = (ImageView) findViewById(me.rishabhkhanna.peopleword.R.id.imSelectUser);
        tvHeading = (TextView) findViewById(me.rishabhkhanna.peopleword.R.id.tvHeading);
        tvDetail = (TextView) findViewById(me.rishabhkhanna.peopleword.R.id.tvDetail);
        cardViewNews = (CardView) findViewById(me.rishabhkhanna.peopleword.R.id.cvNews);
        ivClear = (ImageView) findViewById(me.rishabhkhanna.peopleword.R.id.ivClear);

        tvHeading.setText(thisJson.getHl());
        tvDetail.setText(thisJson.getSyn());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        getChats();
        chatAdapter = new ChatAdapter(chatsList, this);
        recyclerView.setAdapter(chatAdapter);
        Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(100, 100)).into(userImageView);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.first_user_DB,MODE_PRIVATE);
        Boolean firstUser = sharedPreferences.getBoolean(Constants.firstUserKey,true);
        if(firstUser) {
            TapTargetView.showFor(this, TapTarget.forView(userImageView,"Share your Views anonymously","select anonymous user from here"));
            sharedPreferences.edit().putBoolean(Constants.firstUserKey,false).apply();
        }

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ChatActivity.this, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                popupMenu.getMenu().add(1, 1, 1, Profile.getCurrentProfile().getName());
                popupMenu.getMenu().add(1, 2, 2, "Anonymous user");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 1:
                                Picasso.with(ChatActivity.this)
                                        .load(Profile.getCurrentProfile().getProfilePictureUri(100, 100))
                                        .into(userImageView);
                                anonym_user = false;
                                break;
                            case 2:
                                Picasso.with(ChatActivity.this)
                                        .load(me.rishabhkhanna.peopleword.R.drawable.person_placeholder)
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
                        if (chatMsg.isEmpty()) {
                            Toast.makeText(ChatActivity.this, "Message is Empty", Toast.LENGTH_SHORT).show();
                        } else {

                            Chat chat = new Chat(chatMsg, thisJson.getMsid(),
                                    thisJson.getId(), AccessToken.getCurrentAccessToken().getUserId(), anonym_user);

                            socket.emit("new_message", new Gson().toJson(chat));
                            etChat.setText("");
                        }
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
                    builder.setMessage("You are not connected to chat").setPositiveButton("Connect again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            socket.connect();
                            if (AccessToken.getCurrentAccessToken() != null)
                                socket.emit("join_room", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            socket.connect();
                            if (AccessToken.getCurrentAccessToken() != null)
                                socket.emit("join_room", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));
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
                Chat chat = new Gson().fromJson(String.valueOf(data), Chat.class);

                chatsList.add(chat);

                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        chatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition((chatsList.size() - 1));
                    }
                });
            }
        };
        socket.on("from_server", onNewMessage);

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewNews.setVisibility(View.GONE);
            }
        });


        socket.on("ping", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d(TAG, "call: ping recieved");
            }
        });

        if (socket != null) {
            Log.d(TAG, "onCreate: " + socket.connect());
            if (AccessToken.getCurrentAccessToken() != null)
                socket.emit("join_room", new Gson().toJson(new ChatRoom(thisJson.getMsid(), AccessToken.getCurrentAccessToken().getUserId(), thisJson.getId())));

        }

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                recyclerView.scrollToPosition(chatsList.size() - 1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        socket.disconnect();
        super.onDestroy();
    }

    public void getChats() {
        ChatAPI.getChatInstance()
                .retrofit
                .create(getChats.class)
                .getChat(thisJson.getId(), thisJson.getMsid())
                .enqueue(new Callback<ArrayList<Chat>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Chat>> call, Response<ArrayList<Chat>> response) {
                        Log.d(TAG, "onResponse: chat message request : " + call.request());
                        Log.d(TAG, "onResponse: Chat Response : " + response.body());
                        if (response.body() != null) {
                            chatsList.clear();
                            chatsList.addAll(response.body());
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition((chatsList.size() - 1));
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Chat>> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
    }


}

