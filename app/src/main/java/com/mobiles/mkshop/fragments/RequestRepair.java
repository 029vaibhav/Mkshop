package com.mobiles.mkshop.fragments;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;
import com.mobiles.mkshop.adapters.ServiceCenterAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RequestRepair extends Fragment {

    public static String TAG = "service";

    List<RepairPojo> repairList;
    MaterialDialog materialDialog;
    EditText search;

    ListView listView;
    ServiceCenterAdapter serviceCenterAdapter;

    String stringBrand = null, stringModel = null, Stringdate;
    boolean customerstatus = false;


    public static RequestRepair newInstance() {
        RequestRepair fragment = new RequestRepair();

        return fragment;
    }

    public RequestRepair() {
        // Required empty public constructor
        repairList = new ArrayList<RepairPojo>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "RequestRepair";
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_request_repair, container, false);

        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(false, 0)
                .content("please wait")
                .cancelable(false)
                .build();

        listView = (ListView) view.findViewById(R.id.repairlist);
        search = (EditText) view.findViewById(R.id.editsearch);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToListView(listView);


        new ListInitializer().execute();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Myenum.INSTANCE.setRepairPojo(repairList.get(position));

                Fragment fragment = new RepairListItemFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new RepairNewItemFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


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
                serviceCenterAdapter.filter(s);

            }
        });


        return view;
    }


    private class ListInitializer extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            materialDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            Client.INSTANCE.getServiceList(MkShop.AUTH, new Callback<List<RepairPojo>>() {
                @Override
                public void success(List<RepairPojo> repairPojos, Response response) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    repairList = repairPojos;
                    Myenum.INSTANCE.setServiceList(repairList);
                    serviceCenterAdapter = new ServiceCenterAdapter(getActivity(), repairList);
                    listView.setAdapter(serviceCenterAdapter);

                }

                @Override
                public void failure(RetrofitError error) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                        MkShop.toast(getActivity(), "please check your internet connection");
                    else
                        MkShop.toast(getActivity(), error.getMessage());

                }
            });


            return null;
        }
    }
}
