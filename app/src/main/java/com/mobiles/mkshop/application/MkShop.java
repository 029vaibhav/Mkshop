package com.mobiles.mkshop.application;

import android.content.Context;
import android.widget.Toast;

import com.mobiles.mkshop.gcm.Controller;

/**
 * Created by vaibhav on 27/6/15.
 */
public class MkShop extends Controller {

    public static String SCRREN = "";
    public static String Role = "sm";
    public static String Username = "amrut";
    public static String AUTH = "";



    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void toast(Context context, String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }


}
