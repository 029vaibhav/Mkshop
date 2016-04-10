package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.mobiles.mkshop.adapters.ServiceCenterAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;


public class RequestRepair extends Fragment {

    public static String TAG = "service";

    List<ServiceCenterEntity> repairList;
    ProgressDialog materialDialog;
    EditText search;

    RecyclerView listView;
    ServiceCenterAdapter serviceCenterAdapter;


    public static RequestRepair newInstance() {
        RequestRepair fragment = new RequestRepair();

        return fragment;
    }

    public RequestRepair() {
        // Required empty public constructor
        repairList = new ArrayList<ServiceCenterEntity>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "RequestRepair";
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_request_repair, container, false);

        materialDialog = NavigationMenuActivity.materialDialog;

        listView = (RecyclerView) view.findViewById(R.id.repairlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);
        search = (EditText) view.findViewById(R.id.edit_search);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
//        fab.attachToListView(listView);


        listInitializer();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RepairNewItemFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s != null)
                        serviceCenterAdapter.filter(s);
                } catch (NullPointerException e) {

                    repairList = Myenum.INSTANCE.getServiceList(null);
                    if (repairList == null) {
                        listInitializer();
                    } else {
                        serviceCenterAdapter = new ServiceCenterAdapter(RequestRepair.this, repairList);
                        listView.setAdapter(serviceCenterAdapter);

                    }
                }


            }
        });
        return view;
    }


    private void listInitializer() {
        materialDialog.show();
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(getString(R.string.date_format));

        Client.INSTANCE.getServiceList(MkShop.AUTH).enqueue(new Callback<List<ServiceCenterEntity>>() {
            @Override
            public void onResponse(Call<List<ServiceCenterEntity>> call, Response<List<ServiceCenterEntity>> response) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                repairList = response.body();
                sort(repairList, new Comparator<ServiceCenterEntity>() {
                    @Override
                    public int compare(ServiceCenterEntity lhs, ServiceCenterEntity rhs) {
                        return formatter.parseDateTime(lhs.getCreated()).compareTo(formatter.parseDateTime(rhs.getCreated()));
                    }
                });
                reverse(repairList);
                Myenum.INSTANCE.setServiceList(repairList);
                serviceCenterAdapter = new ServiceCenterAdapter(RequestRepair.this, repairList);
                listView.setAdapter(serviceCenterAdapter);
            }

            @Override
            public void onFailure(Call<List<ServiceCenterEntity>> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                    MkShop.toast(getActivity(), t.getMessage());

            }
        });


    }
}
