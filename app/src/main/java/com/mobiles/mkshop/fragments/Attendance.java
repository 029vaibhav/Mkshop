package com.mobiles.mkshop.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.gps.GPSTracker;
import com.mobiles.mkshop.pojos.LoginDetails;
import com.mobiles.mkshop.R;

import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Attendance extends Fragment implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    // TODO: Rename parameter arguments, choose names that match


    SharedPreferences sharedPreferences;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String TAG = "Attendance";
    GPSTracker gps;
    TextView attendance;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    LoginDetails loginDetailsList;

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

        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("DETAIL", null);
        Type type = new TypeToken<LoginDetails>() {
        }.getType();
        loginDetailsList = new Gson().fromJson(json, type);


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {

                    Location start = new Location("A"), end = new Location("B");

                    start.setLatitude(gps.getLatitude());
                    start.setLongitude(gps.getLongitude());
                    end.setLatitude(Double.parseDouble(loginDetailsList.getLocation().getLatitude()));
                    end.setLongitude(Double.parseDouble(loginDetailsList.getLocation().getLongitude()));
                    double distance = start.distanceTo(end);
                    if (distance <= Integer.parseInt(loginDetailsList.getLocation().getRadius())) {
                        Client.INSTANCE.markAttendance(MkShop.AUTH, MkShop.Username, new Callback<String>() {
                            @Override
                            public void success(String s, Response response) {
                                MkShop.toast(getActivity(), s);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                    MkShop.toast(getActivity(), "please check your internet connection");
                                else MkShop.toast(getActivity(), error.getMessage());

                            }
                        });
                    } else {
                        MkShop.toast(getActivity(), "your are not in the coverage area");
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


        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.


        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks((this))
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        locationRequest = LocationRequest.create();
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
                        startLocationUpdates();
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


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient,
                locationRequest,
                this
        ).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                MkShop.toast(getActivity(), "mark your attendance now");
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("RESULT_CANCELED", "User chose not to make required location settings changes.");
                        MkShop.toast(getActivity(), "please turn on the gps to mark your attendance");

                        break;
                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
