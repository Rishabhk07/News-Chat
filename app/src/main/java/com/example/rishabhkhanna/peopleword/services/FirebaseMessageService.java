package com.example.rishabhkhanna.peopleword.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.Topic;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.example.rishabhkhanna.peopleword.views.Activities.BaseActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            RemoteViews remoteView = new RemoteViews(getApplicationContext().getPackageName(),R.layout.notification_layout);
            RemoteViews remoteBigView = new RemoteViews(getApplicationContext().getPackageName(),R.layout.notification_expanded_layout);

            try {
                 bitmap = Picasso.with(this).load(UtilMethods.getImageurl(remoteMessage.getData().get("image"))).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            remoteView.setTextViewText(R.id.tvNotificationHeading,remoteMessage.getData().get("title"));
            remoteBigView.setTextViewText(R.id.tvNotification,remoteMessage.getData().get("title"));
            remoteBigView.setImageViewBitmap(R.id.imNotification,bitmap);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
            String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            remoteBigView.setTextViewText(R.id.tvExNotificationTime,currentTime);
            Log.d(TAG, "onMessageReceived: Current Time" + currentTime);
            remoteView.setTextViewText(R.id.tvNotificationTime,currentTime);
            NotificationCompat.Builder builder  = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getData().get("title"))
//                    .setCustomContentView(remoteView)
                    .setCustomBigContentView(remoteBigView);

            Intent intent = new Intent(this,BaseActivity.class);
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("news_id"));
            intent.putExtra("notification_key",remoteMessage.getData().get("news_id"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this,notificationId,intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
