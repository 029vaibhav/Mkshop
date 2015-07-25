package com.mobiles.mkshop.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Gps.GPSTracker;
import com.mobiles.mkshop.R;


public class Attendance extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String TAG = "Attendance";
    GPSTracker gps;
    TextView attendance;

    public static Attendance newInstance() {
        Attendance fragment = new Attendance();
        return fragment;
    }

    public Attendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN = "Attendance";
        gps = new GPSTracker(getActivity());
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_attendance, container, false);
        attendance = (TextView) viewGroup.findViewById(R.id.attendancetext);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {

                    Location start = new Location("A"), end = new Location("B");

                    start.setLatitude(gps.getLatitude());
                    start.setLongitude(gps.getLongitude());
                    end.setLatitude(gps.getLatitude());
                    end.setLongitude(gps.getLongitude());
                    double distance = start.distanceTo(end);
                    if (distance <= 50) {
                        MkShop.toast(getActivity(),"coverage area");
                    }
                    else
                    {
                        MkShop.toast(getActivity(),"your are not in the coverage area");
                    }


                } else {
                    showSettingsAlert();
                }
            }
        });


        return viewGroup;
    }


    private void showSettingsAlert() {
        // TODO Auto-generated method stub


        LocationRequest mLocationRequest = new LocationRequest();


        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(10000 / 2);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks((this))
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);


        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(LocationSettingsResult result) {
                // TODO Auto-generated method stub
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Fragment  fragment = getFragmentManager().findFragmentByTag(Attendance.TAG);
                        if (fragment == null) {
                            fragment = new Attendance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        MkShop.toast(getActivity(), "Mark your attendance now");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            Log.e("RESOLUTION_REQUIRED", "REQUEST_CHECK_SETTINGS");
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("dfd", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("fdfd", "Location settings are inadequate, and cannot be fixed here. Dialog " +
                                "not created.");
                        break;
                }
            }
        });


    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344 * 1000;

        Log.e("distance", "" + dist);
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        MkShop.toast(getActivity(), "Mark your attendance now");
                      Fragment  fragment = getFragmentManager().findFragmentByTag(Attendance.TAG);
                        if (fragment == null) {
                            fragment = new Attendance();
                        }
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("RESULT_CANCELED", "User chose not to make required location settings changes.");
                        MkShop.toast(getActivity(), "please turn on the gps to mark your attendance");

                        break;
                }
                break;
        }
    }
}
