package com.mobiles.mkshop.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.Location;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GeopointsFragment extends Fragment {


    public static String TAG = "GeopointsFragment";

    ListView listView;
    MaterialDialog materialDialog;


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


        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();

        materialDialog.show();

        Client.INSTANCE.getAllLocation(MkShop.AUTH, new Callback<List<Location>>() {
            @Override
            public void success(final List<Location> locations, Response response) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
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
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                    MkShop.toast(getActivity(), "please check your internet connection");
                else MkShop.toast(getActivity(), error.getMessage());

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
