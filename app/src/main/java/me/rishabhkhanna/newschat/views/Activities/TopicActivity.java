package me.rishabhkhanna.newschat.views.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import me.rishabhkhanna.newschat.Adapters.TopicAdapter;
import me.rishabhkhanna.newschat.Network.API;
import me.rishabhkhanna.newschat.Network.interfaces.getAuth;
import me.rishabhkhanna.newschat.R;
import me.rishabhkhanna.newschat.model.AuthResponse;
import me.rishabhkhanna.newschat.model.Topic;
import me.rishabhkhanna.newschat.utils.Constants;
import me.rishabhkhanna.newschat.utils.TouchHelper;
import me.rishabhkhanna.newschat.utils.UtilMethods;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicActivity extends AppCompatActivity {

    RecyclerView recyclerViewTopic;
    RealmList<Topic> topics = new RealmList<>();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_topic);
        setTitle("Topics");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        recyclerViewTopic = (RecyclerView)findViewById(me.rishabhkhanna.newschat.R.id.rvTopic);
        topics = UtilMethods.getTopics(this);
        TopicAdapter topicAdapter = new TopicAdapter(this, topics);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerViewTopic.setLayoutManager(flexboxLayoutManager);
        recyclerViewTopic.setAdapter(topicAdapter);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ItemTouchHelper.Callback callback = new TouchHelper(topicAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewTopic);
    }


    @Override
    public void onPause() {
        ArrayList<Topic> selectedTopics = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TOPIC_NAME, Context.MODE_PRIVATE);

        for (int i = 0; i < topics.size(); i++) {
            selectedTopics.add(
                    new Topic(topics.get(i).getName()
                            , topics.get(i).getKey(),sharedPreferences.getBoolean(topics.get(i).getKey(), false)));

        }

        realm.copyToRealmOrUpdate(topics);
        realm.commitTransaction();

//        Topics variable is changed after this point
//        Log.d(TAG, "onPause: Topics Fragment Pause");
        String topics = new Gson().toJson(selectedTopics);
        if(AccessToken.getCurrentAccessToken() != null){
            API.getInstance()
                    .retrofit
                    .create(getAuth.class)
                    .updateUserTopics(topics,AccessToken.getCurrentAccessToken().getUserId())
                    .enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
//                            Log.d(TAG, "onResponse: " + response.body());
                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
//                            Log.d(TAG, "onFailure: " + call.request());
                        }
                    });
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
