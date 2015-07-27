package com.mobiles.mkshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.Location;
import com.mobiles.mkshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GeopointsFragment extends Fragment {


    public static String TAG = "GeopointsFragment";

    ListView listView;

    public static GeopointsFragment newInstance() {
        GeopointsFragment fragment = new GeopointsFragment();
        return fragment;
    }

    public GeopointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_geopoints, container, false);

        listView = (ListView) viewGroup.findViewById(R.id.locationList);

        Client.INSTANCE.getAllLocation(MkShop.AUTH,new Callback<List<Location>>() {
            @Override
            public void success(final List<Location> locations, Response response) {

                List<String> items = new ArrayList<String>();
                for (int i = 0; i < locations.size(); i++) {
                    items.add(locations.get(i).getRole());
                }

                ArrayAdapter<String> itemsAdapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                listView.setAdapter(itemsAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Location location = locations.get(position);

                        Fragment fragment = NewGeoLocationItem.newInstance(location.getLatitude(),
                                location.getLongitude(), location.getRole(), location.getRadius(), "" + location.getId());
                        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        fab.attachToListView(listView);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new NewGeoLocationItem();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            }
        });

        return viewGroup;
    }


}
