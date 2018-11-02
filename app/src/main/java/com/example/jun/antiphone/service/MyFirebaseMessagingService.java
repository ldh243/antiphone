package com.example.jun.antiphone.service;

import android.util.Log;

import com.example.jun.antiphone.singleton.MyNotificationManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.toString();
    private static final String DEFAULT_MESSAGE_TITLE = "Student Manager";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, String.format("onNewToken: &s", s));
    }

    // [START_EXCLUDE]
    // There are two types of messages data messages and notification messages. Data messages are handled
    // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
    // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
    // is in the foreground. When the app is in the background an automatically generated notification is displayed.
    // When the user taps on the notification they are returned to the app. Messages containing both notification
    // and data payloads are treated as notification messages. The Firebase console always sends notification
    // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
    // [END_EXCLUDE]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily

        //dont need this
        if(remoteMessage.getData().size() > 0){
            //handle the data message here
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, String.format("onMessageReceived, title: %s", title));
            if (title == null) {

                title = DEFAULT_MESSAGE_TITLE;
            }

            //todo display message herre


            MyNotificationManager.getInstance(this).displayNotification(
                    title, body
            );
            //todo save notification to Database

        }


        //then here we can use the title and body to build a notification
    }
}
