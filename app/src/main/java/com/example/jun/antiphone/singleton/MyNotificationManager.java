package com.example.jun.antiphone.singleton;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.jun.antiphone.R;
import com.example.jun.antiphone.cart.CartActivity;

import util.Constants;

public class MyNotificationManager {


    //id only for future edit
    static final int DEFAULT_NOTIFICATION_ID = 123123;

    private static MyNotificationManager mInstance;
    private Context mContext;

    private MyNotificationManager(Context context) {
        mContext = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }


    public void displayNotification(String title, String body) {

        displayNotification(title, body, DEFAULT_NOTIFICATION_ID);

    }
    public void displayNotification(String title, String body, int notificationId) {


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext, Constants.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_24px)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSound(defaultSoundUri)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .setAutoCancel(true);


        //open notification fragment when receive

        Intent resultIntent = new Intent(mContext, CartActivity.class);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());

        //todo add noti to DB
//        DummyDatabase.getNotificationList().add(0,new NotificationModel(notificationId, title, body));
    }
}
