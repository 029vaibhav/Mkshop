package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.LoginDetails;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceThroughWifi extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    SharedPreferences sharedPreferences;

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static String TAG = "Attendance";

    TextView attendance;
    LoginDetails loginDetailsList;
    ProgressDialog materialDialog;

    WifiManager wifiManager;
    WifiInfo wifiInfo;


    public static AttendanceThroughWifi newInstance() {
        AttendanceThroughWifi fragment = new AttendanceThroughWifi();
        return fragment;

    }

    public AttendanceThroughWifi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyApplication.SCRREN = "Attendance";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_attendance, container, false);
        attendance = (TextView) viewGroup.findViewById(R.id.attendancetext);
        wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("DETAIL", null);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            loginDetailsList = objectMapper.readValue(json, LoginDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        materialDialog = NavigationMenuActivity.materialDialog;


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markAttendance();
            }
        });


        return viewGroup;
    }


    private void markAttendance() {

        materialDialog.show();

        if (loginDetailsList.getLocation() == null) {
            MyApplication.toast(getActivity(), "Ask your admin to provide your location");
            if (materialDialog != null && materialDialog.isShowing())
                materialDialog.dismiss();
            return;
        }


//        Log.e("wifi", "" + wifiInfo.getSSID().replace("\"", "") + " " + loginDetailsList.getLocation().getLatitude() + " " + loginDetailsList.getLocation().getLongitude());


        if (wifiInfo.getSSID().replace("\"", "").equals(loginDetailsList.getLocation().getLatitude()) || wifiInfo.getSSID().replace("\"", "").equals(loginDetailsList.getLocation().getLongitude())) {

            Call<String> stringCall = Client.INSTANCE.markAttendance(MyApplication.AUTH, MyApplication.Username);
            stringCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MyApplication.toast(getActivity(), response.body());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    MyApplication.toast(getActivity(), t.getMessage());
                }
            });

        } else {
            if (materialDialog != null && materialDialog.isShowing())
                materialDialog.dismiss();
            MyApplication.toast(getActivity(), "Connect to proper wifi");
        }


    }

}
