package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Location;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewWifiNames extends Fragment

{

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    ProgressDialog materialDialog;

    public static String TAG = "NewWifiNames";
    private static final String lat = "lat";
    private static final String longi = "longi";
    private static final String prole = "role";
    private static final String id = "id";

    android.location.Location location;

    String[] roleOption = {"Salesman", "Technician", "Receptionist"};
    private String mlat = "lat";
    private String mlongi = "longi";
    private String mrole = "role";
    private String mid = null;

    EditText latitude, longitude;
    AutoCompleteTextView role;
    Button submit;

    public static NewWifiNames newInstance(String param1, String param2, String param3, String param5) {
        NewWifiNames fragment = new NewWifiNames();
        Bundle args = new Bundle();
        args.putString(lat, param1);
        args.putString(longi, param2);
        args.putString(prole, param3);
        args.putString(id, param5);
        fragment.setArguments(args);
        return fragment;
    }

    public NewWifiNames() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mlat = getArguments().getString(lat);
            mlongi = getArguments().getString(longi);
            mrole = getArguments().getString(prole);
            mid = getArguments().getString(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.geo_location_list_item, container, false);

        latitude = (EditText) viewGroup.findViewById(R.id.latitude);
        longitude = (EditText) viewGroup.findViewById(R.id.longitude);
        role = (AutoCompleteTextView) viewGroup.findViewById(R.id.role);
        submit = (Button) viewGroup.findViewById(R.id.submit);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, roleOption);
        role.setThreshold(1);
        role.setAdapter(adapter);

        if (getArguments() != null) {
            latitude.setText(mlat);
            longitude.setText(mlongi);
            role.setText(mrole);
//            radius.setText(mradius);
        }
        materialDialog = NavigationMenuActivity.materialDialog;

        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (latitude.getText().length() == 0 || longitude.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please enter both the wifi or enter Na");
                else if (role.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please enter role");
//                else if (radius.getText().length() == 0)
//                    MkShop.toast(getActivity(), "please enter radius");
                else {
                    materialDialog.show();
                    Location location = new Location();
                    if (mid != null) location.setId(Integer.parseInt(mid));
                    location.setLatitude(latitude.getText().toString());
                    location.setLongitude(longitude.getText().toString());
                    location.setRadius("");
                    location.setRole(role.getText().toString());
                    Client.INSTANCE.setLocation(MyApplication.AUTH, location).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MyApplication.toast(getActivity(), response.body());
                            Fragment fragment = new GeoPointsFragment();
                            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                             MyApplication.toast(getActivity(), t.getMessage());

                        }
                    });

                }


            }
        });

        return viewGroup;
    }

}
