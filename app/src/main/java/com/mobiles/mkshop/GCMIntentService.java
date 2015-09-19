package com.mobiles.mkshop;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.mobiles.mkshop.activities.NotificationDetailActivity;
import com.mobiles.mkshop.gcm.Config;
import com.mobiles.mkshop.gcm.Controller;

import java.util.Random;

/**
 * Created by vaibhav on 26/7/15.
 */
public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";
    public static Notification notification;
    public static NotificationManager notificationManager;
    public static int notification_id = 1;
    private Controller aController = null;

    public GCMIntentService() {
        // Call extended class Constructor GCMBaseIntentService
        super(Config.GOOGLE_SENDER_ID);
    }

    /**
     * Method called on device registered
     */
    @Override
    protected void onRegistered(Context context, String registrationId) {

        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        if (aController == null)
            aController = (Controller) getApplicationContext();

        Log.i(TAG, "Device registered: regId = " + registrationId);
        aController.displayMessageOnScreen(context, "Your device registred with GCM");
        // Log.d("NAME", MainActivity.name);
        aController.register(context, "vaibs", "vaibhs@gd.com", registrationId);
    }

    /**
     * Method called on device unregistred
     */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        if (aController == null)
            aController = (Controller) getApplicationContext();
        Log.i(TAG, "Device unregistered");
        aController.displayMessageOnScreen(context, getString(R.string.gcm_unregistered));
        aController.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message from GCM server
     */
    @Override
    protected void onMessage(Context context, Intent intent) {

        if (aController == null)
            aController = (Controller) getApplicationContext();

        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("message");

        aController.displayMessageOnScreen(context, message);
        // notifies user
        postNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     */
    @Override
    protected void onDeletedMessages(Context context, int total) {

        if (aController == null)
            aController = (Controller) getApplicationContext();

        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        aController.displayMessageOnScreen(context, message);
        // notifies user
        postNotification(context, message);
    }

    /**
     * Method called on Error
     */
    @Override
    public void onError(Context context, String errorId) {

        if (aController == null)
            aController = (Controller) getApplicationContext();

        Log.i(TAG, "Received error: " + errorId);
        aController.displayMessageOnScreen(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {

        if (aController == null)
            aController = (Controller) getApplicationContext();

        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        aController.displayMessageOnScreen(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Create a notification to inform the user that server has sent a message.
     */
//    private static staticvoid generateNotification(Context context, String message) {
//        int icon = R.mipmap.ic_mkshop;
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager)
//                context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notification = new Notification(icon, message, when);
//
//        String title = context.getString(R.string.app_name);
//
//        Intent notificationIntent = new Intent(context, NotificationDetailActivity.class);
//        notificationIntent.putExtra("message", message);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
////        Intent intentx = new Intent(context, MainActivity.class);
////        intentx.putExtra("message", message);
////        intentx.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
////                Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        Random rand = new Random();
//        int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
//
//        PendingIntent intent =
//                PendingIntent.getActivity(context, randomNum, notificationIntent, 0);
//        notification.setLatestEventInfo(context, title, message, intent);
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // Play default notification sound
//        notification.defaults |= Notification.DEFAULT_SOUND;
//
//        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
//
//        // Vibrate if vibrate is enabled
//
//
//
//        notification.defaults |= Notification.DEFAULT_VIBRATE;
//        notificationManager.notify(randomNum, notification);
//
//    }
    public static void postNotification(Context context, String message) {

        Random rand = new Random();
        notification_id = rand.nextInt((1000 - 1) + 1) + 1;

        Intent notificationIntent = new Intent(context, NotificationDetailActivity.class);
        notificationIntent.putExtra("message", message);
        PendingIntent contentIntent = PendingIntent.getActivity(context, notification_id, notificationIntent, 0);

        int icon = R.mipmap.ic_mkshop;


        // call the default notification
        notification = new Notification.Builder(context)

                .setContentTitle("New Sale")
                .setContentText(message)
                .setSmallIcon(icon)
                .setContentIntent(contentIntent)
                .addAction(android.R.drawable.ic_menu_view, "View details", contentIntent)
                .setAutoCancel(true)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(notification_id, notification);
    }

}
