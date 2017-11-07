package me.rishabhkhanna.newschat.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Log;

import me.rishabhkhanna.newschat.model.AuthDetails;
import me.rishabhkhanna.newschat.model.Topic;

import java.util.ArrayList;

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
    public static String getImageurl(String urlId, String width,String height) {
        return "https://timesofindia.indiatimes.com/thumb.cms?photoid=" +
                urlId + "&width="+width+"&height="+height+"&resizemode=1";
    }

    //get all topics of news
    public static RealmList<Topic> getTopics(Context context) {
        RealmList<Topic> selectedTopic =
                new RealmList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.TOPIC_POS_DB, Context.MODE_PRIVATE);

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Topic> realmQuery = realm.where(Topic.class);
        RealmResults<Topic> results = realmQuery.findAllSorted("position", Sort.ASCENDING);


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

    public static Boolean isNetConnected(Context context){
        ConnectivityManager connectivityManager  = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo  = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    public static void showSnackBar(String message, int id, Activity activity){
        Snackbar snackbar = Snackbar.make(activity.findViewById(id), message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }


}
