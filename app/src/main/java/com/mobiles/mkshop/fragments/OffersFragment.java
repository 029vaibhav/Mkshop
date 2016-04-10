package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.NotificationAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.Message;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersFragment extends Fragment {

    public static String TAG = "OffersFragment";


    RecyclerView recyclerView;
    EditText search;
    NotificationAdapter listItemAdapter;
    ProgressDialog materialDialog;


    public static OffersFragment newInstance() {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_offers, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.notificationList);
        search = (EditText) viewGroup.findViewById(R.id.edit_search);

        materialDialog = NavigationMenuActivity.materialDialog;


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && listItemAdapter != null)
                    listItemAdapter.filter(s);

            }
        });


        materialDialog.show();
        Client.INSTANCE.getNotificationDetail(MkShop.AUTH, MkShop.Role).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                listItemAdapter = new NotificationAdapter(getActivity(), response.body());
                recyclerView.setAdapter(listItemAdapter);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                    MkShop.toast(getActivity(), t.getMessage());

            }
        });


        return viewGroup;

    }


}
