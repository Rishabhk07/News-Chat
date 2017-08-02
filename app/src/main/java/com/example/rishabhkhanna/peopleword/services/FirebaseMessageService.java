package com.example.rishabhkhanna.peopleword.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Topic;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.example.rishabhkhanna.peopleword.views.Activities.BaseActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import io.realm.RealmList;

import static com.example.rishabhkhanna.peopleword.utils.UtilMethods.getTopics;

/**
 * Created by rishabhkhanna on 02/08/17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    public static final String TAG = "FirebaseMessageService";
    public static final int notificationId = 123;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0 ){
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
            NotificationCompat.Builder builder  = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_apps_black_24dp)
                    .setColor(Color.BLUE)
                    .setContentText(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("detail"));
            Intent intent = new Intent(this,BaseActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,123,intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId,builder.build());
        }
        super.onMessageReceived(remoteMessage);
    }
}
