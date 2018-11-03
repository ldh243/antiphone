package com.example.jun.antiphone;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import util.Constants;

import static util.Constants.CHANNEL_ID;

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.toString();

    @Override
    public void onCreate() {
        super.onCreate();

        // register to notification topic
        registerToFirebaseTopic(Constants.FIREBASE_TOPIC_NAME);
        createNotificationChannel();
    }

    private void registerToFirebaseTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed to firebase";
                        if (!task.isSuccessful()) {
                            msg = "Failded to Subscribe";
                        }
                        Log.d(TAG, String.format("onComplete: %s", msg));
//                        Log.d(TAG, msg);

//                        Toast.makeText(MyApplication.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        //if the channel is already exist, this do nothing so dont worry running this many times
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = Constants.CHANNEL_NAME;
            String description = Constants.CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
