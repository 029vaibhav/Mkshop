package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.UserListItemAdpater;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.User;
import com.mobiles.msm.pojos.models.UserListAttendance;

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

        MyApplication.SCRREN = "UserListFragment";
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


        GetUserList();


        return viewGroup;


    }

    private void GetUserList() {

        final ProgressDialog dialog = NavigationMenuActivity.materialDialog;
        Client.INSTANCE.getUserList(MyApplication.AUTH).enqueue(new Callback<List<User>>() {
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
                MyApplication.toast(getActivity(), t.getMessage());
            }
        });

    }
}
