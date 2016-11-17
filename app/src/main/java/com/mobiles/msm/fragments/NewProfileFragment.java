package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.NewUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewProfileFragment extends Fragment {


    EditText name, mobile, email, username, password, qualification, address;
    TextView submit;
    AutoCompleteTextView role;
    String[] roleOption = {"Admin", "Salesman", "Technician", "Receptionist", "Promoter"};
    NewUser newUser;


    public static NewProfileFragment newInstance(String param1, String param2) {
        NewProfileFragment fragment = new NewProfileFragment();

        return fragment;
    }

    public NewProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_new_profile, container, false);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, roleOption);


        name = (EditText) viewGroup.findViewById(R.id.name);
        mobile = (EditText) viewGroup.findViewById(R.id.mobileNo);
        email = (EditText) viewGroup.findViewById(R.id.email);
        username = (EditText) viewGroup.findViewById(R.id.username);
        password = (EditText) viewGroup.findViewById(R.id.password);
        qualification = (EditText) viewGroup.findViewById(R.id.qualification);
        address = (EditText) viewGroup.findViewById(R.id.address);

        role = (AutoCompleteTextView) viewGroup.findViewById(R.id.role);
        role.setThreshold(1);
        role.setAdapter(adapter);

        submit = (TextView) viewGroup.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please enter name");
                } else if (mobile.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please enter mobile no");

                } else if (username.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please enter username");

                } else if (password.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please enter password");

                } else if (role.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please enter role");

                } else {

                    newUser = new NewUser();
                    newUser.setName(name.getText().toString());
                    if (address.getText().length() > 0)
                        newUser.setAddress(address.getText().toString());
                    else
                        newUser.setAddress("");
                    newUser.setRole(role.getText().toString());
                    newUser.setUsername(username.getText().toString());
                    newUser.setPassword(password.getText().toString());
                    if (qualification.getText().length() > 0)
                        newUser.setQualification(qualification.getText().toString());
                    else
                        newUser.setQualification("");
                    newUser.setMobile(mobile.getText().toString());
                    if (email.getText().length() > 0)
                        newUser.setEmail(email.getText().toString());
                    else
                        newUser.setEmail("");


                    new SendData().execute();

                }

            }
        });


        return viewGroup;
    }


    private class SendData extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = NavigationMenuActivity.materialDialog;
        }


        @Override
        protected Void doInBackground(Void... params) {

            Client.INSTANCE.createUser(MyApplication.AUTH, newUser).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();
                    MyApplication.toast(getActivity(), "user created successfully");
                    Fragment fragment = new UserListFragment();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();

                        MyApplication.toast(getActivity(), t.getMessage());
                    Log.e("error", t.getMessage().toString());

                }
            });


            return null;
        }
    }
}
