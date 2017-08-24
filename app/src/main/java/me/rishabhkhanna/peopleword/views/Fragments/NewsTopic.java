package me.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rishabhkhanna.peopleword.Adapters.TopicAdapter;
import me.rishabhkhanna.peopleword.Network.API;
import me.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import me.rishabhkhanna.peopleword.model.AuthResponse;
import me.rishabhkhanna.peopleword.model.Topic;
import me.rishabhkhanna.peopleword.utils.Constants;
import me.rishabhkhanna.peopleword.utils.TouchHelper;
import me.rishabhkhanna.peopleword.utils.UtilMethods;
import com.facebook.AccessToken;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsTopic extends Fragment {


    RecyclerView recyclerViewTopic;
    RealmList<Topic> topics = new RealmList<>();
    public static final String TAG = "NewsTopic";
    public NewsTopic() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(me.rishabhkhanna.peopleword.R.layout.fragment_news_topic, container, false);
        recyclerViewTopic = (RecyclerView) root.findViewById(me.rishabhkhanna.peopleword.R.id.rvTopic);
        topics = UtilMethods.getTopics(getContext());
        TopicAdapter topicAdapter = new TopicAdapter(getContext(), topics);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerViewTopic.setLayoutManager(flexboxLayoutManager);
        recyclerViewTopic.setAdapter(topicAdapter);
        ItemTouchHelper.Callback callback = new TouchHelper(topicAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewTopic);
        return root;
    }

    @Override
    public void onPause() {
        ArrayList<Topic> selectedTopics = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.TOPIC_NAME, Context.MODE_PRIVATE);

        for (int i = 0; i < topics.size(); i++) {
            selectedTopics.add(
                    new Topic(topics.get(i).getName()
                            , topics.get(i).getKey(),sharedPreferences.getBoolean(topics.get(i).getKey(), false)));

        }
        Log.d(TAG, "onPause: Topics Fragment Pause");
        String topics = new Gson().toJson(selectedTopics);
        if(AccessToken.getCurrentAccessToken() != null){
            API.getInstance()
                    .retrofit
                    .create(getAuth.class)
                    .updateUserTopics(topics,AccessToken.getCurrentAccessToken().getUserId())
                    .enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            Log.d(TAG, "onResponse: " + response.body());
                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + call.request());
                        }
                    });
        }

        super.onPause();
    }
}
