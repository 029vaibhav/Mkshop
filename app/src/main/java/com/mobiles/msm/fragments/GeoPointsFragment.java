package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GeoPointsFragment extends Fragment {


    public static String TAG = "GeoPointsFragment";

    ListView listView;
    ProgressDialog materialDialog;


    public static GeoPointsFragment newInstance() {
        GeoPointsFragment fragment = new GeoPointsFragment();
        return fragment;
    }

    public GeoPointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_geopoints, container, false);

        listView = (ListView) viewGroup.findViewById(R.id.locationList);


        materialDialog = NavigationMenuActivity.materialDialog;

        materialDialog.show();

        Call<List<Location>> allLocation = Client.INSTANCE.getAllLocation(MyApplication.AUTH);
        allLocation.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, final Response<List<Location>> response) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                List<String> items = new ArrayList<String>();
                for (int i = 0; i < response.body().size(); i++) {
                    items.add(response.body().get(i).getRole());
                }

                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                listView.setAdapter(itemsAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Location location = response.body().get(position);

                        Fragment fragment = NewWifiNames.newInstance(location.getLatitude(),
                                location.getLongitude(), location.getRole(), "" + location.getId());
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                 MyApplication.toast(getActivity(), t.getMessage());

            }
        });


        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
//        fab.attachToListView(listView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new NewWifiNames();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            }
        });

        return viewGroup;
    }


}

