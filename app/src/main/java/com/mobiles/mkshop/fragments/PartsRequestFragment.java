package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.PartRequestAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.models.PartsRequests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PartsRequestFragment extends Fragment {

    public static String TAG = "PartsRequestFragment";

    PartRequestAdapter partRequestAdapter;
    List<PartsRequests> partsRequestsList;
    ProgressDialog materialDialog;
    ListView listView;


    public static PartsRequestFragment newInstance() {
        PartsRequestFragment fragment = new PartsRequestFragment();
        return fragment;
    }

    public PartsRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN = "PartsRequestFragment";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_parts_request, container, false);
        partsRequestsList = new ArrayList<PartsRequests>();
        final EditText search = (EditText) viewGroup.findViewById(R.id.edit_search);
        materialDialog = NavigationMenuActivity.materialDialog;
        listView = (ListView) viewGroup.findViewById(R.id.repairlist);
        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        listInitializer();
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if (text != null && partRequestAdapter != null)
                    partRequestAdapter.Filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Myenum.INSTANCE.setRequestRepair(partsRequestsList.get(position));
                Fragment fragment = PartsRequestListItemFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = PartsRequestNewItemFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        return viewGroup;
    }


    private void listInitializer() {
        if (materialDialog != null)
            materialDialog.show();
        {
            Client.INSTANCE.getPartList(MkShop.AUTH).enqueue(new Callback<List<PartsRequests>>() {
                @Override
                public void onResponse(Call<List<PartsRequests>> call, Response<List<PartsRequests>> response) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();
                    partsRequestsList = response.body();
                    Myenum.INSTANCE.setPartsRequestsList(partsRequestsList);
                    partRequestAdapter = new PartRequestAdapter(getActivity(), partsRequestsList);
                    listView.setAdapter(partRequestAdapter);
                }

                @Override
                public void onFailure(Call<List<PartsRequests>> call, Throwable t) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                        MkShop.toast(getActivity(), t.getMessage());


                }
            });

        }

    }
}
