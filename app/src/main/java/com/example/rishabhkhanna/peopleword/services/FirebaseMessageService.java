package com.example.rishabhkhanna.peopleword.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Topic;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.example.rishabhkhanna.peopleword.views.Activities.BaseActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import io.realm.RealmList;
import okhttp3.internal.Util;

import static com.example.rishabhkhanna.peopleword.utils.UtilMethods.getTopics;

/**
 * Created by rishabhkhanna on 02/08/17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    public static final String TAG = "FirebaseMessageService";
    public static int notificationId = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0 ){
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
            Bitmap bitmap = null;
            try {
                 bitmap = Picasso.with(this).load(UtilMethods.getImageurl(remoteMessage.getData().get("image"))).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            NotificationCompat.Builder builder  = new NotificationCompat.Builder(this)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setSmallIcon(R.drawable.ic_apps_black_24dp)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(
                            bitmap
                    ).setBigContentTitle(remoteMessage.getData().get("title"))
                            .setSummaryText(remoteMessage.getData().get("detail")));

            Intent intent = new Intent(this,BaseActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,123,intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            notificationId = new Random().nextInt();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Log.d(TAG, "onMessageReceived: " + notificationId);
            notificationManager.notify(notificationId,builder.build());
        }
        super.onMessageReceived(remoteMessage);
    }
}
