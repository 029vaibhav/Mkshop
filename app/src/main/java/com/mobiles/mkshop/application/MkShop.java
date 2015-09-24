package com.mobiles.mkshop.application;

import android.app.Fragment;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.gcm.Controller;

/**
 * Created by vaibhav on 27/6/15.
 */
public class MkShop extends Controller {

    public static String SCRREN = "";
    public static String Role = "sm";
    public static String Username = "";
    public static String AUTH = "";
    public static String gcm_defaultSenderId = "";
    public static String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static String REGISTRATION_COMPLETE = "registrationComplete";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void toast(Context context, String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();


    }



}
