package com.mobiles.mkshop.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.adapters.RevenueCompartorAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.Sales;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueCompatorFragment extends Fragment {

    public static String TAG = "RevenueCompatorFragment";
    String[] whichString = {"Mobile", "brandService", "Accessory"};


    TextView toDate, fromDate, totalQuantity, totalRevenue, category;
    String sFromdate, sToDate;
    int tempQuantity = 0, tempRevenue = 0;
    RevenueCompartorAdapter listItemAdapter;
    RecyclerView listView;

    public static RevenueCompatorFragment newInstance() {
        RevenueCompatorFragment fragment = new RevenueCompatorFragment();

        return fragment;
    }

    public RevenueCompatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_revenue_compator, container, false);

        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
        toDate = (TextView) viewGroup.findViewById(R.id.toDate);
        category = (TextView) viewGroup.findViewById(R.id.category);

        totalQuantity = (TextView) viewGroup.findViewById(R.id.totalQuantity);
        totalRevenue = (TextView) viewGroup.findViewById(R.id.totalRevenue);
        listView = (RecyclerView) viewGroup.findViewById(R.id.saleslistview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);


        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fromDate.getText().toString().equalsIgnoreCase("from") || toDate.getText().toString().equalsIgnoreCase("to")) {
                    MkShop.toast(getActivity(), "please select date range");
                } else {
                    {
                        final List<String> statusOfParts = Arrays.asList(getResources().getStringArray(R.array.revenueComparator));
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final ArrayAdapter<String> aa1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, statusOfParts);
                        builder.setSingleChoiceItems(aa1, 0, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                tempQuantity = 0;
                                tempRevenue = 0;
                                String text = statusOfParts.get(which);
                                category.setText(text);
                                if (which == 0 || which == 2) {
                                    Client.INSTANCE.getpricecompator(MkShop.AUTH, sFromdate, sToDate, whichString[which]).enqueue(new Callback<List<Sales>>() {
                                        @Override
                                        public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                                            listItemAdapter = new RevenueCompartorAdapter(getActivity(), response.body());
                                            listView.setAdapter(listItemAdapter);
                                            for (int i = 0; i < response.body().size(); i++) {
                                                tempQuantity = tempQuantity + Integer.parseInt(response.body().get(i).getQuantity());
                                                if (response.body().get(i).getPrice() != null)
                                                    tempRevenue = tempRevenue + Integer.parseInt(response.body().get(i).getPrice());
                                            }
                                            totalRevenue.setText("" + tempRevenue);
                                            totalQuantity.setText("" + tempQuantity);

                                        }

                                        @Override
                                        public void onFailure(Call<List<Sales>> call, Throwable t) {

                                                MkShop.toast(getActivity(), t.getMessage());

                                        }
                                    });
                                } else if (which == 1) {
                                    Client.INSTANCE.getPriceComparatorTech(MkShop.AUTH, sFromdate, sToDate).enqueue(new Callback<List<Sales>>() {
                                        @Override
                                        public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {


                                            listItemAdapter = new RevenueCompartorAdapter(getActivity(), response.body());
                                            listView.setAdapter(listItemAdapter);
                                            for (int i = 0; i < response.body().size(); i++) {
                                                tempQuantity = tempQuantity + Integer.parseInt(response.body().get(i).getQuantity());
                                                if (response.body().get(i).getPrice() != null)
                                                    tempRevenue = tempRevenue + Integer.parseInt(response.body().get(i).getPrice());
                                            }
                                            totalRevenue.setText("" + tempRevenue);
                                            totalQuantity.setText("" + tempQuantity);

                                        }

                                        @Override
                                        public void onFailure(Call<List<Sales>> call, Throwable t) {

                                                MkShop.toast(getActivity(), t.getMessage());
                                        }
                                    });
                                }
                            }

                        });
                        AlertDialog alert = builder.create();
                        alert.show();



                    }
                }

            }
        });


        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (fromDate.getText().toString().equalsIgnoreCase("from")) {
                    MkShop.toast(getActivity(), "please select starting date");
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {


                            String date = MkShop.checkDigit(i3) + "-" + MkShop.checkDigit(i2 + 1) + "-" + i;


                            toDate.setText(date);
                            DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);

                            sToDate = dt.toString("yyyy-MM-dd");


                        }
                    }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
                    datePickerDialog.show();
                }
            }
        });


        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {


                        String date = MkShop.checkDigit(i3) + "-" + MkShop.checkDigit(i2 + 1) + "-" + i;


                        fromDate.setText(date);
                        DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);
                        sFromdate = dt.toString("yyyy-MM-dd");
                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });


        return viewGroup;

    }


}
