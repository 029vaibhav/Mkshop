package com.mobiles.mkshop.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.mobiles.mkshop.gcm.Controller;

import java.util.Calendar;
import java.util.Date;

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
    public static final String LAST_MODIFIED_DATE = "date";


    private static MkShop instance;

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }

    public static String getUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        return sharedPreferences.getString("USERNAME", null);
    }

    public static String getAUTH() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        return sharedPreferences.getString("AUTH", null);
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void toast(Context context, String error) {

        try {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }


    }


    public static int GetImage(String ImageName) {

        return getContext().getResources().getIdentifier(ImageName, "drawable", getContext().getPackageName());
//        return ContextCompat.getDrawable(getContext(), getContext().getResources().getIdentifier(ImageName, "drawable", getContext().getPackageName()));
    }

    public static String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


}
