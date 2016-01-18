package com.mobiles.mkshop.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.google.android.gms.gcm.GcmListenerService;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NotificationDetailActivity;

import java.util.Random;

/**
 * Created by Akki on 9/24/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

//        if (from.startsWith("/topics/")) {
            // message received from some topic.
//        } else {
            // normal downstream message.
//        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {


        Random rand = new Random();
        int notification_id = rand.nextInt((1000 - 1) + 1) + 1;

//        Intent notificationIntent = new Intent(context, NotificationDetailActivity.class);
//        notificationIntent.putExtra("message", message);
//        PendingIntent contentIntent = PendingIntent.getActivity(context, notification_id, notificationIntent, 0);

        int icon = R.mipmap.ic_mkshop;


        // call the default notification
//        notification = new Notification.Builder(context)
//
//                .setContentTitle("New Sale")
//                .setContentText(message)
//                .setSmallIcon(icon)
//                .setContentIntent(contentIntent)
//                .addAction(android.R.drawable.ic_menu_view, "View details", contentIntent)
//                .setAutoCancel(true)
////                .setPriority(Notification.PRIORITY_HIGH)
////                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .build();
//        notificationManager =
//                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notificationManager.notify(notification_id, notification);
//New
//
//


        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtra("message", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notification_id, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle("New Sale")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notification_id, notificationBuilder.build());
    }
}
