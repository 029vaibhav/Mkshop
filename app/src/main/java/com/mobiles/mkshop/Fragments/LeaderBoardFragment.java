package com.mobiles.mkshop.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.Adapters.LeaderBoardItemAdapter;
import com.mobiles.mkshop.Adapters.TabsPagerAdapterLeader;
import com.mobiles.mkshop.Application.Client;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Application.Myenum;
import com.mobiles.mkshop.Pojos.Leader;
import com.mobiles.mkshop.R;

import org.joda.time.DateTime;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LeaderBoardFragment extends Fragment {


    private FragmentActivity myContext;
    public static String TAG = "LeaderBoardFragment";
    Boolean quantityBoolean = false;
    Boolean priceBoolean = false;
    TextView toDate, fromDate;
    ViewPager viewPager;
    String sFromdate, sToDate;
    TabsPagerAdapterLeader adapter;

    public static LeaderBoardFragment newInstance() {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        return fragment;
    }

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {

        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN = "LeaderBoardFragment";


//        List<Leader> salesList = new ArrayList<Leader>();
//        Leader sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sm");
//        sales.setQuantity("20");
//        sales.setPrice("1200");
//        salesList.add(sales);
//        sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sm");
//        sales.setQuantity("20");
//        sales.setPrice("2000");
//        salesList.add(sales);
//        sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sm");
//        sales.setQuantity("30");
//        sales.setPrice("12000");
//        salesList.add(sales);
//        sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sc");
//        sales.setQuantity("10");
//        sales.setPrice("1200");
//        salesList.add(sales);
//        sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sm");
//        sales.setQuantity("10");
//        sales.setPrice("12000");
//        salesList.add(sales);
//        sales = new Leader();
//        sales.setName("vaibhav");
//        sales.setRole("sc");
//        sales.setQuantity("50");
//        sales.setPrice("100");
//        salesList.add(sales);
//
//
//        Myenum.INSTANCE.setLeaderList(salesList);

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_leader_board, container, false);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.pager_leader);
        TextView quantity = (TextView) viewGroup.findViewById(R.id.quantity);
        TextView revenue = (TextView) viewGroup.findViewById(R.id.revenue);
        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
        toDate = (TextView) viewGroup.findViewById(R.id.toDate);


        adapter = new TabsPagerAdapterLeader(myContext.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) viewGroup.findViewById(R.id.sliding_tabs_leader);
        tabLayout.setupWithViewPager(viewPager);


        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityBoolean = !quantityBoolean;

                int index = viewPager.getCurrentItem();
                TabsPagerAdapterLeader adapter = ((TabsPagerAdapterLeader) viewPager.getAdapter());
                LeaderBoardList fragment = adapter.getFragment(index);
                RecyclerView listView = (RecyclerView) fragment.getView().findViewById(R.id.saleslistview);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                listView.setLayoutManager(linearLayoutManager);

                LeaderBoardItemAdapter adapter2 = (LeaderBoardItemAdapter) listView.getAdapter();
                adapter2.sortquantiy(quantityBoolean);


            }
        });


        revenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceBoolean = !priceBoolean;
                int index = viewPager.getCurrentItem();
                TabsPagerAdapterLeader adapter = ((TabsPagerAdapterLeader) viewPager.getAdapter());
                LeaderBoardList fragment = adapter.getFragment(index);
                RecyclerView listView = (RecyclerView) fragment.getView().findViewById(R.id.saleslistview);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                listView.setLayoutManager(linearLayoutManager);
                LeaderBoardItemAdapter adapter2 = (LeaderBoardItemAdapter) listView.getAdapter();
                adapter2.sortprice(priceBoolean);
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
                            new GetData().execute();


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

    private class GetData extends AsyncTask<Void, Void, Void> {

        MaterialDialog materialDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            materialDialog = new MaterialDialog.Builder(getActivity())
                    .progress(true, 0)
                    .cancelable(false)
                    .build();

            materialDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Client.INSTANCE.getLeaderBoard(sFromdate, sToDate, new Callback<List<Leader>>() {
                @Override
                public void success(List<Leader> leaders, Response response) {

                    materialDialog.dismiss();
                    Myenum.INSTANCE.setLeaderList(leaders);
                    materialDialog.dismiss();
                    adapter = new TabsPagerAdapterLeader(myContext.getSupportFragmentManager());
                    viewPager.setAdapter(adapter);



                }

                @Override
                public void failure(RetrofitError error) {

                    materialDialog.dismiss();
                    if(error.getKind().equals(RetrofitError.Kind.NETWORK))
                        MkShop.toast(getActivity(),"Please check your internet connection");
                    else if(error!=null)
                        MkShop.toast(getActivity(), error.getMessage());



                }
            });

            return null;

        }
    }
    }

