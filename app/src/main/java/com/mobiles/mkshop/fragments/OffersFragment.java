package com.mobiles.mkshop.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.adapters.NotificationAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.Notification;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OffersFragment extends Fragment {

    public static String TAG = "OffersFragment";


    RecyclerView recyclerView;

    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        return fragment;
    }

    public OffersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_offers, container, false);
        recyclerView= (RecyclerView) viewGroup.findViewById(R.id.notificationList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        Client.INSTANCE.getNotificationDetail(MkShop.AUTH,MkShop.Role, new Callback<List<Notification>>() {
            @Override
            public void success(List<Notification> notifications, Response response) {

                NotificationAdapter listItemAdapter = new NotificationAdapter(getActivity(), notifications);
                recyclerView.setAdapter(listItemAdapter);


            }

            @Override
            public void failure(RetrofitError error) {

                if(error.getKind().equals(RetrofitError.Kind.NETWORK))
                    MkShop.toast(getActivity(),"check your internet connection");

            }
        });



        return viewGroup;

    }





}
