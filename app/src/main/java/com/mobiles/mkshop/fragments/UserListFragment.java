package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import com.mobiles.mkshop.adapters.UserListItemAdpater;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.User;
import com.mobiles.mkshop.pojos.models.UserListAttendance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 4/7/15.
 */
public class UserListFragment extends Fragment {


    public static String TAG = "UserListFragment";

    UserListItemAdpater userListItemAdpater;
    List<UserListAttendance> userListAttendances;
    RecyclerView recyclerView;


    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        return fragment;
    }

    public UserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MkShop.SCRREN = "UserListFragment";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_user_list, container, false);


        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.repairlist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        // fab.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewProfileFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });


        new GetUserLsit().execute();


        return viewGroup;


    }

    private class GetUserLsit extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = NavigationMenuActivity.materialDialog;
        }


        @Override
        protected Void doInBackground(Void... params) {
            Client.INSTANCE.getUserList(MkShop.AUTH).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    userListItemAdpater = new UserListItemAdpater(UserListFragment.this, response.body());
                    recyclerView.setAdapter(userListItemAdpater);
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                        MkShop.toast(getActivity(), t.getMessage());
                }
            });


            return null;
        }
    }
}
