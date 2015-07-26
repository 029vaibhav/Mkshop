package com.mobiles.mkshop.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.adapters.RevenueCompartorAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.PriceCompartorService;
import com.mobiles.mkshop.R;

import org.joda.time.DateTime;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RevenueCompatorFragment extends Fragment {

    public static String TAG = "RevenueCompatorFragment";


    TextView toDate, fromDate, totalQuantity, totalRevenue, category;
    String sFromdate, sToDate;
    int tempQuantity = 0, tempRevenue = 0;
    RevenueCompartorAdapter listItemAdapter;
    RecyclerView listView;

    public static RevenueCompatorFragment newInstance(String param1, String param2) {
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
         listView = (RecyclerView)viewGroup.findViewById(R.id.saleslistview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(linearLayoutManager);


        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (fromDate.getText().toString().equalsIgnoreCase("from") || toDate.getText().toString().equalsIgnoreCase("to")) {
                    MkShop.toast(getActivity(), "please select date range");
                } else {
                    {
                        new MaterialDialog.Builder(getActivity())
                                .items(R.array.revenueComparator)
                                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View view, final int which, CharSequence text) {

                                        category.setText(text.toString());

                                        Client.INSTANCE.getpricecompator(sFromdate, sToDate, ""+which, new Callback<List<PriceCompartorService>>() {
                                            @Override
                                            public void success(List<PriceCompartorService> response1, Response response) {


                                                listItemAdapter = new RevenueCompartorAdapter(getActivity(), response1);
                                                listView.setAdapter(listItemAdapter);


//                                                switch (which) {
//                                                    case 0:
//
//                                                        List<Sales> list = new Gson().fromJson(result, new TypeToken<List<Sales>>() {
//                                                        }.getType());
//
//
//
//                                                        break;
//                                                    case 3:
//                                                        List<Sales> list2 = new Gson().fromJson(result, new TypeToken<List<Sales>>() {
//                                                        }.getType());
//
//                                                        listItemAdapter = new RevenueCompartorAdapter(getActivity(),list2,null);
//                                                        listView.setAdapter(listItemAdapter);
//
//                                                        break;
//                                                    case 1:
//
//                                                        List<PriceCompartorService> list3 = new Gson().fromJson(result, new TypeToken<List<PriceCompartorService>>() {
//                                                        }.getType());
//
//                                                        listItemAdapter = new RevenueCompartorAdapter(getActivity(),null, list3);
//                                                        listView.setAdapter(listItemAdapter);
//
//                                                        break;
//                                                    case 2:
//
//                                                        List<Sales> list4  = new Gson().fromJson(result, new TypeToken<List<Sales>>() {
//                                                        }.getType());
//
//                                                        listItemAdapter = new RevenueCompartorAdapter(getActivity(),list4, null);
//                                                        listView.setAdapter(listItemAdapter);
//                                                        break;
//                                                }


                                            }

                                            @Override
                                            public void failure(RetrofitError error) {

                                                MkShop.toast(getActivity(),error.getMessage());

                                            }
                                        });

                                        return true;
                                    }
                                })
                                .positiveText("select")
                                .show();
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


                            String date = checkDigit(i3) + "-" + checkDigit(i2 + 1) + "-" + i;


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


                        String date = checkDigit(i3) + "-" + checkDigit(i2 + 1) + "-" + i;


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

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


}
