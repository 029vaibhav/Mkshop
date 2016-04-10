package com.mobiles.mkshop.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.IncentiveAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.IncentiveEntity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Incentive extends Fragment {

    public static String TAG = "Attendance";
    RecyclerView recyclerView;
    ProgressDialog materialDialog;


    public static Incentive newInstance() {
        Incentive fragment = new Incentive();


        return fragment;
    }

    public Incentive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_incentive, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.incentiveList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        //   fab.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewIncentiveFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });

        materialDialog = NavigationMenuActivity.materialDialog;

        materialDialog.show();

        Call<List<IncentiveEntity>> incentiveList = Client.INSTANCE.getIncentiveList(MkShop.AUTH);
        incentiveList.enqueue(new Callback<List<IncentiveEntity>>() {
                                  @Override
                                  public void onResponse(Call<List<IncentiveEntity>> call, Response<List<IncentiveEntity>> response) {

                                      if (materialDialog != null && materialDialog.isShowing())
                                          materialDialog.dismiss();
                                      IncentiveAdapter incentiveAdapter = new IncentiveAdapter(Incentive.this, response.body());
                                      recyclerView.setAdapter(incentiveAdapter);

                                  }

                                  @Override
                                  public void onFailure(Call<List<IncentiveEntity>> call, Throwable t) {

                                      if (materialDialog != null && materialDialog.isShowing())
                                          materialDialog.dismiss();
                                      MkShop.toast(getActivity(), t.getMessage());

                                  }
                              }

        );

        return viewGroup;
    }


}
