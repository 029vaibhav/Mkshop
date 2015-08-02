package com.mobiles.mkshop.gcm;

/**
 * Created by vaibhav on 26/7/15.
 */
public interface Config {



    // CONSTANTS
    static final String YOUR_SERVER_URL =  "http://mobiweb.co.in/mk/webservice/noti/gcmserver/register.php";
    // YOUR_SERVER_URL : Server url where you have placed your server files
    // Google project id
    static final String GOOGLE_SENDER_ID = "530000338474";  // Place here your Google project id
    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Android Example";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.androidexample.gcm.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
}
