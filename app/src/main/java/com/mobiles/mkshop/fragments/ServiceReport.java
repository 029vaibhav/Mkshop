package com.mobiles.mkshop.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.adapters.TabsPagerAdapterService;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.R;

import org.joda.time.DateTime;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vaibhav on 3/7/15.
 */
public class ServiceReport extends Fragment {


    private FragmentActivity myContext;
    public static String TAG = "ServiceReport";
    TextView toDate, fromDate;
    String sFromdate, sToDate;
    MaterialDialog materialDialog;

     ViewPager viewPager ;
    TabsPagerAdapterService adapter;

    public static ServiceReport newInstance() {
        ServiceReport fragment = new ServiceReport();
        return fragment;
    }

    public ServiceReport() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {

        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_service_report, container, false);

        MkShop.SCRREN = "ServiceReport";

        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();


         viewPager = (ViewPager) viewGroup.findViewById(R.id.pager_report);
        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
        toDate = (TextView) viewGroup.findViewById(R.id.toDate);


        adapter = new TabsPagerAdapterService(myContext.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) viewGroup.findViewById(R.id.sliding_tabs_report);
        tabLayout.setupWithViewPager(viewPager);


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

                            executequery();


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
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });




        return viewGroup;

    }

    private void executequery() {


        materialDialog.show();

        Client.INSTANCE.getServiceReport(MkShop.AUTH,sFromdate, sToDate, new Callback<List<RepairPojo>>() {
            @Override
            public void success(final List<RepairPojo> serviceList, Response response) {


                Myenum.INSTANCE.setServiceList(serviceList);
                if(materialDialog !=null &&materialDialog.isShowing())
                materialDialog.dismiss();
                adapter = new TabsPagerAdapterService(myContext.getSupportFragmentManager());
                viewPager.setAdapter(adapter);




            }

            @Override
            public void failure(RetrofitError error) {
                if(materialDialog !=null &&materialDialog.isShowing())
                materialDialog.dismiss();
                MkShop.toast(getActivity(), error.getMessage());


            }
        });


    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
