package com.mobiles.mkshop.activities;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.Attendance;
import com.mobiles.mkshop.fragments.GeopointsFragment;
import com.mobiles.mkshop.fragments.LeaderBoardFragment;
import com.mobiles.mkshop.fragments.PartsRequestFragment;
import com.mobiles.mkshop.fragments.ProfileFragment;
import com.mobiles.mkshop.fragments.RequestRepair;
import com.mobiles.mkshop.fragments.RevenueCompatorFragment;
import com.mobiles.mkshop.fragments.SaleFragment;
import com.mobiles.mkshop.fragments.SalesReport;
import com.mobiles.mkshop.fragments.ServiceReport;
import com.mobiles.mkshop.fragments.UserListFragment;
import com.mobiles.mkshop.fragments.ViewProductFragment;
import com.mobiles.mkshop.gcm.Config;
import com.mobiles.mkshop.gcm.Controller;
import com.mobiles.mkshop.NavigationDrawerCallbacks;
import com.mobiles.mkshop.NavigationDrawerFragment;
import com.mobiles.mkshop.pojos.LoginDetails;
import com.mobiles.mkshop.pojos.UserType;
import com.mobiles.mkshop.R;

import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {

    Controller aController;
    AsyncTask<Void, Void, Void> mRegisterTask;

    SharedPreferences sharedPreferences;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    Bitmap bitmapAvtar = null;
    LoginDetails loginDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("DETAIL", null);
        Type type = new TypeToken<LoginDetails>() {
        }.getType();
        loginDetailsList = new Gson().fromJson(json, type);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        mNavigationDrawerFragment.setUserData(loginDetailsList.getName(), "", loginDetailsList.getPhoto());
        // populate the navigation drawer
    }


//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;


        if (!MkShop.Role.equalsIgnoreCase(UserType.ADMIN.name())) {

            switch (position) {
                case 0:
                    //Attendance
                    fragment = getFragmentManager().findFragmentByTag(Attendance.TAG);
                    if (fragment == null) {
                        fragment = new Attendance();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 1: //sale
                    fragment = getFragmentManager().findFragmentByTag(SaleFragment.TAG);
                    if (fragment == null) {
                        fragment = new SaleFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 2:
                    //request part
                    fragment = getFragmentManager().findFragmentByTag(PartsRequestFragment.TAG);
                    if (fragment == null) {
                        fragment = new PartsRequestFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 3:
                    //service
                    fragment = getFragmentManager().findFragmentByTag(RequestRepair.TAG);
                    if (fragment == null) {
                        fragment = new RequestRepair();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;

                case 4:
                    //View Product
                    fragment = getFragmentManager().findFragmentByTag(ViewProductFragment.TAG);
                    if (fragment == null) {
                        fragment = new ViewProductFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;

            }
        } else if
                (MkShop.Role.equalsIgnoreCase(UserType.ADMIN.name())) {
            switch (position) {
                case 0:
                    //sales Report
                    fragment = new SalesReport();
//                    if (fragment == null) {
//                        fragment = new SalesReport();
//                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 1: //service Report
                    fragment = new ServiceReport();
//                    if (fragment == null) {
//                        fragment = new ServiceReport();
//                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 2:
                    //request part
                    fragment = getFragmentManager().findFragmentByTag(PartsRequestFragment.TAG);
                    if (fragment == null) {
                        fragment = new PartsRequestFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 3:
                    //User data
                    fragment = getFragmentManager().findFragmentByTag(UserListFragment.TAG);
                    if (fragment == null) {
                        fragment = new UserListFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 4:
                    //LeaderBoard
                    fragment = getFragmentManager().findFragmentByTag(LeaderBoardFragment.TAG);
                    if (fragment == null) {
                        fragment = new LeaderBoardFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                //My profile
                case 6:
                    fragment = getFragmentManager().findFragmentByTag(GeopointsFragment.TAG);
                    if (fragment == null) {
                        fragment = new GeopointsFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 5:
                    fragment = getFragmentManager().findFragmentByTag(RevenueCompatorFragment.TAG);
                    if (fragment == null) {
                        fragment = new RevenueCompatorFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else if (MkShop.SCRREN.equalsIgnoreCase("RepairListItemFragment")) {
            Fragment fragment = getFragmentManager().findFragmentByTag(RequestRepair.TAG);
            if (fragment == null) {
                fragment = new RequestRepair();
            }
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (MkShop.SCRREN.equalsIgnoreCase("PartsRequestListItemFragment")) {
            Fragment fragment = getFragmentManager().findFragmentByTag(PartsRequestFragment.TAG);
            if (fragment == null) {
                fragment = new PartsRequestFragment();
            }
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else {
            super.onBackPressed();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.profile) {

            Fragment fragment = getFragmentManager().findFragmentByTag(ProfileFragment.TAG);
            if (fragment == null) {
                fragment = ProfileFragment.newInstance(MkShop.Username);
            }
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


            return true;
        } else if (id == R.id.notification) {
            notification();
        }


        return super.onOptionsItemSelected(item);
    }

    private void notification() {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                Config.DISPLAY_MESSAGE_ACTION));

        final String regId = GCMRegistrar.getRegistrationId(MainActivity.this);

        Log.e("retg", regId);
        if (regId.equals("")) {

            // Register with GCM
            GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);

        } else {

            // Device is already registered on GCM Server
            if (GCMRegistrar.isRegisteredOnServer(this)) {

                // Skips registration.
                Toast.makeText(getApplicationContext(), "Already registered with GCM Server", Toast.LENGTH_LONG).show();

            } else {

                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.

                final Context context = MainActivity.this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {

                        // Register on our server
                        // On server creates a new user
                        aController.register(MainActivity.this, "vaibs", "vaibns@gma.com", regId);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };

                // execute AsyncTask
                mRegisterTask.execute(null, null, null);
            }
        }

    }

    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);

            // Waking up mobile if it is sleeping
            aController.acquireWakeLock(getApplicationContext());

            // Display message on the screen


            Toast.makeText(getApplicationContext(), "Got Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            aController.releaseWakeLock();
        }
    };

    @Override
    protected void onDestroy() {
        // Cancel AsyncTask
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            // Unregister Broadcast Receiver
            unregisterReceiver(mHandleMessageReceiver);

            //Clear internal resources.
            GCMRegistrar.onDestroy(this);

        } catch (Exception e) {
            Log.e("UnRegister", "> " + e.getMessage());
        }
        super.onDestroy();
    }


}
