package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Function;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.IncentiveUserlistAdapter;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Sales;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncentiveUserListFragment extends Fragment {

    public static String TAG = "IncentiveUserListFragment";

    String id;
    String message;
    RecyclerView listView;
    ProgressDialog materialDialog;

    public static IncentiveUserListFragment newInstance(String message, String param1) {
        IncentiveUserListFragment fragment = new IncentiveUserListFragment();
        Bundle args = new Bundle();
        args.putString("id", param1);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    public IncentiveUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments() != null ? getArguments().getString("id") : "";
        message = getArguments() != null ? getArguments().getString("message") : "";

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_incentive_user_list, container, false);


        TextView textView = (TextView) viewGroup.findViewById(R.id.message);
        textView.setText(message);
        listView = (RecyclerView) viewGroup.findViewById(R.id.userList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);

        materialDialog = NavigationMenuActivity.materialDialog;
        materialDialog.show();

        Client.INSTANCE.getIncentiveUserList(MyApplication.AUTH, id).enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                ListMultimap<String, Sales> multimap = Multimaps.index(response.body(), new Function<Sales, String>() {
                    public String apply(Sales source) {
                        return source.getUsername();
                    }

                });
                IncentiveUserlistAdapter incentiveUserlistAdapter = new IncentiveUserlistAdapter(IncentiveUserListFragment.this, multimap, id, message);
                listView.setAdapter(incentiveUserlistAdapter);

            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                    MyApplication.toast(getActivity(), t.getMessage());

            }
        });
        return viewGroup;
    }


}
