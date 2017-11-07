package me.rishabhkhanna.peopleword.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.preference.CheckBoxPreference;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import me.rishabhkhanna.peopleword.R;
import me.rishabhkhanna.peopleword.model.NewsJson;
import me.rishabhkhanna.peopleword.utils.UtilMethods;
import me.rishabhkhanna.peopleword.views.Activities.BaseActivity;
import me.rishabhkhanna.peopleword.views.Activities.ChatActivity;
import me.rishabhkhanna.peopleword.views.Activities.DetailNewsActivity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by rishabhkhanna on 02/08/17.
 */

public class FirebaseMessageService extends FirebaseMessagingService {
    public static final String TAG = "FirebaseMessageService";
    public static int notificationId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
            Bitmap bitmap = null;
            RemoteViews remoteView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_layout);
            RemoteViews remoteBigView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification_expanded_layout);

            try {
                bitmap = Picasso.with(this).load(UtilMethods.getImageurl(remoteMessage.getData().get("image"),"1400","960")).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            remoteView.setTextViewText(R.id.tvNotificationHeading, remoteMessage.getData().get("title"));
            remoteBigView.setTextViewText(R.id.tvNotification, remoteMessage.getData().get("title"));
            remoteBigView.setImageViewBitmap(R.id.imNotification, bitmap);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
            String currentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            remoteBigView.setTextViewText(R.id.tvExNotificationTime, currentTime);

            remoteView.setTextViewText(R.id.tvNotificationTime, currentTime);
            NotificationCompat.Builder builder;
            builder = new NotificationCompat.Builder(this)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.news_logo_small)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setCustomContentView(remoteView)
                    .setCustomBigContentView(remoteBigView);


            Intent intent = new Intent(this, BaseActivity.class);
//            Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().get("table_key"));
            intent.putExtra("table_key", remoteMessage.getData().get("table_key"));
            intent.putExtra("fromNotification",true);
            intent.putExtra("fromNotification",true);
            intent.putExtra("news_id",remoteMessage.getData().get("news_id"));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationId = Integer.parseInt(remoteMessage.getData().get("news_id"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Log.d(TAG, "onMessageReceived: " + notificationId);
            notificationManager.notify(notificationId, builder.build());
        }
        super.onMessageReceived(remoteMessage);
    }
}
