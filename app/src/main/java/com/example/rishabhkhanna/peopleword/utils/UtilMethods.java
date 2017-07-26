package com.example.rishabhkhanna.peopleword.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.model.AuthDetails;
import com.example.rishabhkhanna.peopleword.model.Topic;

import java.util.ArrayList;

import butterknife.internal.Utils;

/**
 * Created by rishabhkhanna on 14/07/17.
 */

public class UtilMethods {
    public static final String TAG = "UtilMethods";
    public static String getImageurl(String urlId){
        return "http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                urlId + "&width=600&height=500&resizemode=1";
    }

    public static ArrayList<Topic> getTopics(){
        ArrayList<Topic> selectedTopic = new ArrayList<>();
        selectedTopic.add(new Topic("Briefs","briefs"));
        selectedTopic.add(new Topic("Entertainment","entertainment"));
        selectedTopic.add(new Topic("Top News","top_news"));

        selectedTopic.add(new Topic("India","india"));
        selectedTopic.add(new Topic("Good Governance","good_gov"));
        selectedTopic.add(new Topic("TV","tv"));

        selectedTopic.add(new Topic("World","world"));

        selectedTopic.add(new Topic("Sports","sports"));

        selectedTopic.add(new Topic("Cricket","cricket"));
        selectedTopic.add(new Topic("Business","business"));
        selectedTopic.add(new Topic("Education","education"));

        selectedTopic.add(new Topic("Life Style","life_style"));
        selectedTopic.add(new Topic("Automotive","automotive"));
        selectedTopic.add(new Topic("Environment","environment"));
        return selectedTopic;
    }

    public static final AuthDetails getAuthDetails(Context context, String dbName){
        SharedPreferences  sharedPreferences  = context.getSharedPreferences(dbName,Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.LOGIN_TOKEN,"null");
        String user_id = sharedPreferences.getString(Constants.LOGIN_USER_ID,"null");
        AuthDetails details = new AuthDetails(token,user_id);
        return details;
    }

    public static final ArrayList<Topic> getUserTopics(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.TOPIC_NAME,Context.MODE_PRIVATE);
        ArrayList<Topic> topics = new ArrayList<>();
        ArrayList<Topic> totalTopics = UtilMethods.getTopics();
        for(int i = 0 ;i < totalTopics.size(); i++){
            if(sharedPreferences.getBoolean(totalTopics.get(i).getKey(),true)){
                topics.add(totalTopics.get(i));
            }
        }
        Log.d(TAG, "getUserTopics: " + topics.get(0));
        return topics;
    }



}
