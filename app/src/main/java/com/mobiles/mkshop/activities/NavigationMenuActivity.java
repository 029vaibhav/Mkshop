package com.mobiles.mkshop.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.CircleImageView;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.AttendanceThroughWifi;
import com.mobiles.mkshop.fragments.ExpenseManagerFragment;
import com.mobiles.mkshop.fragments.GeoPointsFragment;
import com.mobiles.mkshop.fragments.Incentive;
import com.mobiles.mkshop.fragments.LeaderBoardFragment;
import com.mobiles.mkshop.fragments.OffersFragment;
import com.mobiles.mkshop.fragments.SparePartFragment;
import com.mobiles.mkshop.fragments.ProfileFragment;
import com.mobiles.mkshop.fragments.RequestRepair;
import com.mobiles.mkshop.fragments.RevenueCompatorFragment;
import com.mobiles.mkshop.fragments.SaleFragment;
import com.mobiles.mkshop.fragments.SalesReport;
import com.mobiles.mkshop.fragments.SendNotificationFragment;
import com.mobiles.mkshop.fragments.ServiceReport;
import com.mobiles.mkshop.fragments.SplashFragment;
import com.mobiles.mkshop.fragments.UserListFragment;
import com.mobiles.mkshop.fragments.UserSalesService;
import com.mobiles.mkshop.fragments.ViewProductFragment;
import com.mobiles.mkshop.gcm.RegistrationIntentService;
import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.LoginDetails;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences;
    LoginDetails loginDetailsList;
    NavigationView navigationView;
    long back_pressed;
    CircleImageView imageView;
    TextView name;
    public static ProgressDialog materialDialog;

    @Override
    protected void onPause() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
        super.onPause();
    }

    protected void navigation() {

        MkShop.AUTH = sharedPreferences.getString("AUTH", null);
        MkShop.Username = sharedPreferences.getString("USERNAME", null);
        String json = sharedPreferences.getString("DETAIL", null);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            loginDetailsList = objectMapper.readValue(json, LoginDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (loginDetailsList != null) {
            MkShop.Role = loginDetailsList.getUser().getRole();
            if (loginDetailsList.getUser().getPhoto() != null && loginDetailsList.getUser().getPhoto().length() > 0 && !loginDetailsList.getUser().getPhoto().equals("")) {
                Picasso.with(this).load(loginDetailsList.getUser().getPhoto().replace("\\", "")).into(imageView);
            } else {
                Bitmap bitmapAvtar = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
                imageView.setImageBitmap(bitmapAvtar);
            }
            name.setText(loginDetailsList.getUser().getName());
            setMenuAccordingToRole(MkShop.Role, navigationView);
        } else {
            Toast.makeText(NavigationMenuActivity.this, "Please login again", Toast.LENGTH_SHORT).show();
            super.onDestroy();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logUser();
        setContentView(R.layout.activity_navigation_menu);

        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setTitle();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final View.OnClickListener originalToolbarListener = toggle.getToolbarNavigationClickListener();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    toggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else {
                    toggle.setDrawerIndicatorEnabled(true);
                    toggle.setToolbarNavigationClickListener(originalToolbarListener);

                }
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        imageView = (CircleImageView) headerView.findViewById(R.id.user_photo);
        name = ((TextView) headerView.findViewById(R.id.name));
        navigation();
        navigationView.setNavigationItemSelectedListener(this);

        materialDialog = new ProgressDialog(this);
        materialDialog.setMessage("please wait");
        materialDialog.setCancelable(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();


    }

    private void setMenuAccordingToRole(String role, NavigationView navigationView) {

        if (role.equalsIgnoreCase(UserType.TECHNICIAN.name())) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_navigation_technician);
        } else if (role.equalsIgnoreCase(UserType.ADMIN.name())) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_navigation_admin);
        } else if (role.equalsIgnoreCase(UserType.RECEPTIONIST.name())) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_navigation_recep);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_navigation_salesman);
        }

    }

    private void setTitle() {
        getSupportActionBar().setTitle("Mk shop");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis())
                super.onBackPressed();
            else {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    Toast.makeText(getBaseContext(), "Press once again to exit!",
                            Toast.LENGTH_SHORT).show();
                    back_pressed = System.currentTimeMillis();
                } else {
                    super.onBackPressed();
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        if (menu.size() == 3) {
            if (sharedPreferences.getBoolean("NOTI", false)) {
                menu.getItem(2).setTitle("Disable notification");
            } else {
                menu.getItem(2).setTitle("Enable notification");

            }
            NavigationMenuActivity.this.invalidateOptionsMenu();

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logOut) {

            Call<Void> logout = Client.INSTANCE.logout(MkShop.Username);
            logout.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    sharedPreferences.edit().putString("AUTH", null).apply();
                    sharedPreferences.edit().putString("USERNAME", null).apply();
                    sharedPreferences.edit().putString("DETAIL", null).apply();
                    Intent intent = new Intent(NavigationMenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    MkShop.toast(NavigationMenuActivity.this, t.getMessage());

                }
            });

        } else if (id == R.id.profile) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.TAG);
            if (fragment == null) {
                fragment = ProfileFragment.newInstance(MkShop.Username);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


        } else if (id == R.id.notification) {
            Boolean noti = sharedPreferences.getBoolean("NOTI", false);
            if (noti) {
                sharedPreferences.edit().putBoolean("NOTI", !noti).apply();

            } else {
                sharedPreferences.edit().putBoolean("NOTI", !noti).apply();

                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);

            }
        } else if (id == android.R.id.home) {

            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount < 1) {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.sales_report) {
            //sales Report
            fragment = SalesReport.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.service_report) {
            fragment = ServiceReport.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.user_data) {
            fragment = getSupportFragmentManager().findFragmentByTag(UserListFragment.TAG);
            if (fragment == null) {
                fragment = UserListFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.leader_board) {
            //LeaderBoard
            fragment = getSupportFragmentManager().findFragmentByTag(LeaderBoardFragment.TAG);
            if (fragment == null) {
                fragment = LeaderBoardFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


        } else if (id == R.id.revenue_comparator) {
            fragment = getSupportFragmentManager().findFragmentByTag(RevenueCompatorFragment.TAG);
            if (fragment == null) {
                fragment = RevenueCompatorFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.send_notification) {
            fragment = getSupportFragmentManager().findFragmentByTag(SendNotificationFragment.TAG);
            if (fragment == null) {
                fragment = SendNotificationFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.incentive) {
            fragment = getSupportFragmentManager().findFragmentByTag(Incentive.TAG);
            if (fragment == null) {
                fragment = Incentive.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.expense_manager) {
            fragment = getSupportFragmentManager().findFragmentByTag(ExpenseManagerFragment.TAG);
            if (fragment == null) {
                fragment = ExpenseManagerFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.wifi) {
            //set location
            fragment = getSupportFragmentManager().findFragmentByTag(GeoPointsFragment.TAG);
            if (fragment == null) {
                fragment = GeoPointsFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.attendace) {
            fragment = getSupportFragmentManager().findFragmentByTag(AttendanceThroughWifi.TAG);
            if (fragment == null) {
                fragment = AttendanceThroughWifi.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.sale) {
            fragment = getSupportFragmentManager().findFragmentByTag(SaleFragment.TAG);
            if (fragment == null) {
                fragment = SaleFragment.newInstance(null, null);
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.parts) {

            fragment = getSupportFragmentManager().findFragmentByTag(SparePartFragment.TAG);
            if (fragment == null) {
                fragment = SparePartFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.service_center) {
            //service
            fragment = getSupportFragmentManager().findFragmentByTag(RequestRepair.TAG);
            if (fragment == null) {
                fragment = RequestRepair.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.view_product) {
            //View Product
            fragment = ViewProductFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.message) {
            fragment = getSupportFragmentManager().findFragmentByTag(OffersFragment.TAG);
            if (fragment == null) {
                fragment = OffersFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.my_work) {
            fragment = getSupportFragmentManager().findFragmentByTag(UserSalesService.TAG);
            if (fragment == null) {
                fragment = UserSalesService.newInstance();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logUser() {
        Crashlytics.setUserIdentifier(MkShop.Role);
        Crashlytics.setUserName(MkShop.Username);
    }

}
