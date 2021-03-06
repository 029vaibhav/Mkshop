package com.mobiles.msm.gcm;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.mobiles.msm.application.FontsOverride;
import com.orm.SugarApp;

import java.util.Random;

import io.fabric.sdk.android.Fabric;

/**
 * Created by vaibhav on 26/7/15.
 */
public class Controller extends MultiDexApplication {

    private final int MAX_ATTEMPTS = 5;
    private final int BACKOFF_MILLI_SECONDS = 2000;
    private final Random random = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FontsOverride.setDefaultFont(this, "MONOSPACE", "museo.ttf");

    }

}
