package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.SparePartAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.LoginDetails;
import com.mobiles.mkshop.pojos.models.SparePart;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SparePartFragment extends Fragment {

    public static String TAG = "PartsRequestFragment";

    SparePartAdapter sparePartAdapter;
    List<SparePart> sparePartList;
    ProgressDialog materialDialog;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;

    public static SparePartFragment newInstance() {
        SparePartFragment fragment = new SparePartFragment();
        return fragment;
    }

    public SparePartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MkShop.SCRREN = "PartsRequestFragment";
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_parts_request, container, false);
        sparePartList = new ArrayList<SparePart>();
        final EditText search = (EditText) viewGroup.findViewById(R.id.edit_search);
        materialDialog = NavigationMenuActivity.materialDialog;
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.repair_list);
        FloatingActionButton fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        listInitializer();
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                if (text != null && sparePartAdapter != null)
                    sparePartAdapter.Filter(text);
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

        UserType role = getRole();
        if (role == UserType.TECHNICIAN) {
            fab.setVisibility(View.INVISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = NewSparePartFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });
        return viewGroup;
    }

    private UserType getRole() {

        if (MkShop.Role != null) {
            return UserType.valueOf(MkShop.Role.toUpperCase());
        }
        String json = sharedPreferences.getString("DETAIL", null);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginDetails loginDetails = objectMapper.readValue(json, LoginDetails.class);
            String role = loginDetails.getUser().getRole();
            return UserType.valueOf(role);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return UserType.TECHNICIAN;
    }


    private void listInitializer() {
        if (materialDialog != null)
            materialDialog.show();
        {
            sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
            String sToDate;
            sToDate = sharedPreferences.getString(MkShop.LAST_VIEWED_DATE_SPARE_PART, null);
            if (sToDate == null) {
                DateTime dateTime = new DateTime(2015, 1, 1, 0, 0);
                sToDate = dateTime.toString("yyyy-MM-dd'T'HH:mm:ss");
            }

            Client.INSTANCE.getPartList(MkShop.AUTH, sToDate).enqueue(new Callback<List<SparePart>>() {
                @Override
                public void onResponse(Call<List<SparePart>> call, Response<List<SparePart>> response) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                    storeCurrentDate();
                    sparePartList = response.body();
                    for (SparePart sparePart : sparePartList) {
                        SparePart byId = SparePart.findById(SparePart.class, sparePart.getId());
                        if (byId != null) {
                            byId.setBrand(sparePart.getBrand());
                            byId.setCompatibleMobile(sparePart.getCompatibleMobile());
                            byId.setType(sparePart.getType());
                            byId.setDescription(sparePart.getDescription());
                            byId.setQuantity(sparePart.getQuantity());
                            byId.save();
                        } else {
                            sparePart.save();
                        }
                    }
                    sparePartList = SparePart.listAll(SparePart.class);
                    sparePartAdapter = new SparePartAdapter(SparePartFragment.this, sparePartList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(sparePartAdapter);
                }

                @Override
                public void onFailure(Call<List<SparePart>> call, Throwable t) {
                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                    MkShop.toast(getActivity(), t.getMessage());


                }
            });

        }

    }

    private void storeCurrentDate() {

        DateTime dateTime = new DateTime(DateTimeZone.UTC);
        String currentDateTime = dateTime.minusSeconds(30).toString("yyyy-MM-dd'T'HH:mm:ss");
        sharedPreferences.edit().putString(MkShop.LAST_VIEWED_DATE_SPARE_PART, currentDateTime).apply();
    }
}
