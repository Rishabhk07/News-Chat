package com.example.rishabhkhanna.peopleword.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.model.AuthDetails;
import com.example.rishabhkhanna.peopleword.model.Topic;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by rishabhkhanna on 14/07/17.
 */

public class UtilMethods {
    public static final String TAG = "UtilMethods";

    //get image url from imageId
    public static String getImageurl(String urlId) {
        return "http://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                urlId + "&width=1400&height=960&resizemode=1";
    }

    //get all topics of news
    public static RealmList<Topic> getTopics(Context context) {
        RealmList<Topic> selectedTopic =
                new RealmList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.TOPIC_POS_DB, Context.MODE_PRIVATE);

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Topic> realmQuery = realm.where(Topic.class);
        RealmResults<Topic> results = realmQuery.findAllSorted("position", Sort.ASCENDING);
        results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Topic>>() {
            @Override
            public void onChange(RealmResults<Topic> topics, OrderedCollectionChangeSet changeSet) {
                Log.d(TAG, "onChange: " + topics.get(0).getName());
            }
        });

        if (results.isEmpty()) {
            realm.beginTransaction();
            selectedTopic.clear();

            Log.d(TAG, "getTopics: results are null");
            selectedTopic.add(new Topic("Briefs", "briefs", 0));
            selectedTopic.add(new Topic("Entertainment", "entertainment", 1));
            selectedTopic.add(new Topic("Top News", "top_news", 2));

            selectedTopic.add(new Topic("India", "india", 3));
            selectedTopic.add(new Topic("Good Governance", "good_governance", 4));
            selectedTopic.add(new Topic("TV", "tv", 5));

            selectedTopic.add(new Topic("World", "world", 6));

            selectedTopic.add(new Topic("Sports", "sports", 7));

            selectedTopic.add(new Topic("Cricket", "cricket", 8));
            selectedTopic.add(new Topic("Business", "business", 9));
            selectedTopic.add(new Topic("Education", "education", 10));

            selectedTopic.add(new Topic("Life Style", "life_style", 11));
            selectedTopic.add(new Topic("Automotive", "automotive", 12));
            selectedTopic.add(new Topic("Environment", "environment", 13));
            realm.copyToRealmOrUpdate(selectedTopic);

            realm.commitTransaction();
            return selectedTopic;
        } else {
            selectedTopic.clear();
            selectedTopic.addAll(results);
            Log.d(TAG, "getTopics: is not null");
            return selectedTopic;

        }

    }

    // get auth details of current login user
    public static final AuthDetails getAuthDetails(Context context, String dbName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.LOGIN_TOKEN, "null");
        String user_id = sharedPreferences.getString(Constants.LOGIN_USER_ID, "null");
        AuthDetails details = new AuthDetails(token, user_id);
        return details;
    }

    // get topics which user has marked as true
    public static final ArrayList<Topic> getUserTopics(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.TOPIC_NAME, Context.MODE_PRIVATE);
        ArrayList<Topic> topics = new ArrayList<>();
        RealmList<Topic> totalTopics = UtilMethods.getTopics(context);
        if (totalTopics.size() != 0) {
            for (int i = 0; i < totalTopics.size(); i++) {
                if (sharedPreferences.getBoolean(totalTopics.get(i).getKey(), false)) {
                    topics.add(totalTopics.get(i));
                }
            }
        }
//        Log.d(TAG, "getUserTopics: " + topics.get(0));
        return topics;
    }

    //get page number from topic
    public static int getPageFromTopic(String topic) {
        switch (topic) {
            case "briefs":
                return 0;
            case "top_news":
                return 1;
            case "entertainment":
                return 2;
            case "india":
                return 3;
            case "good_governance":
                return 13;
            case "tv":
                return 9;
            case "world":
                return 4;
            case "sports":
                return 5;
            case "cricket":
                return 6;
            case "business":
                return 7;
            case "education":
                return 8;
            case "life_style":
                return 11;
            case "automotive":
                return 10;
            case "environment":
                return 12;
            default:
                return 0;

        }
    }

    //get Profile Picture url from facebook user_id

    public static String getProfilePicutre(String user_id){
        String url = "https://graph.facebook.com/"+ user_id + "/picture?type=normal&redirect=false";
        return url;
    }


}
