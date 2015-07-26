package com.mobiles.mkshop.application;

import android.content.Context;
import android.widget.Toast;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.gcm.Controller;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vaibhav on 27/6/15.
 */
public class MkShop extends Controller {

    public static String SCRREN = "";
    public static String Role = "sm";
    public static String Username = "amrut";


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/pacifico.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public static void toast(Context context, String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
    }


}
