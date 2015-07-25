package com.mobiles.mkshop.Activities;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Fragments.Attendance;
import com.mobiles.mkshop.Fragments.LeaderBoardFragment;
import com.mobiles.mkshop.Fragments.PartsRequestFragment;
import com.mobiles.mkshop.Fragments.ProfileFragment;
import com.mobiles.mkshop.Fragments.RequestRepair;
import com.mobiles.mkshop.Fragments.RevenueCompatorFragment;
import com.mobiles.mkshop.Fragments.SaleFragment;
import com.mobiles.mkshop.Fragments.SalesReport;
import com.mobiles.mkshop.Fragments.ServiceReport;
import com.mobiles.mkshop.Fragments.UserListFragment;
import com.mobiles.mkshop.Fragments.ViewProductFragment;
import com.mobiles.mkshop.NavigationDrawerCallbacks;
import com.mobiles.mkshop.NavigationDrawerFragment;
import com.mobiles.mkshop.Pojos.LoginDetails;
import com.mobiles.mkshop.Pojos.UserType;
import com.mobiles.mkshop.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {

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
                case 5:
                    //My Profile
                    fragment = getFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                    if (fragment == null) {
                        fragment = ProfileFragment.newInstance(loginDetailsList.getUsername());
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
                case 5:
                    fragment = getFragmentManager().findFragmentByTag(ProfileFragment.TAG);
                    if (fragment == null) {
                        fragment = ProfileFragment.newInstance(MkShop.Username);
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case 6:
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
        }

        return super.onOptionsItemSelected(item);
    }


    public Bitmap getImageBitmapFromUrl(URL url) {
        Bitmap bm = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return bm;
            }
            conn.connect();
            InputStream is = conn.getInputStream();

            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                bm = BitmapFactory.decodeStream(bis);
            } catch (OutOfMemoryError ex) {
                bm = null;
            }
            bis.close();
            is.close();
        } catch (Exception e) {
        }

        return bm;
    }


}
