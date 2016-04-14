package com.mobiles.mkshop.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.TabsPagerAdapterService;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 3/7/15.
 */
public class ServiceReport extends Fragment {


    private FragmentActivity myContext;
    public static String TAG = "ServiceReport";
    TextView toDate, fromDate;
    String sFromDate, sToDate;
    ProgressDialog materialDialog;

    ViewPager viewPager;
    TabsPagerAdapterService adapter;
    TabLayout tabLayout;

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

        materialDialog = NavigationMenuActivity.materialDialog;


        viewPager = (ViewPager) viewGroup.findViewById(R.id.pager_report);
        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
        toDate = (TextView) viewGroup.findViewById(R.id.toDate);


        adapter = new TabsPagerAdapterService(myContext.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) viewGroup.findViewById(R.id.sliding_tabs_report);
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

                            if (datePicker.isShown()) {
                                String date = MkShop.checkDigit(i3) + "-" + MkShop.checkDigit(i2 + 1) + "-" + i;


                                toDate.setText(date);
                                DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);

                                sToDate = dt.toString("yyyy-MM-dd");

                                executequery();
                            }


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
                        sFromDate = dt.toString("yyyy-MM-dd");
                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });


        return viewGroup;

    }

    private void executequery() {


        materialDialog.show();

        Call<List<ServiceCenterEntity>> serviceReport = Client.INSTANCE.getServiceReport(MkShop.AUTH, sFromDate, sToDate);
        serviceReport.enqueue(new Callback<List<ServiceCenterEntity>>() {
                                  @Override
                                  public void onResponse(Call<List<ServiceCenterEntity>> call, Response<List<ServiceCenterEntity>> response) {
                                      Myenum.INSTANCE.setServiceList(response.body());
                                      if (materialDialog != null && materialDialog.isShowing())
                                          materialDialog.dismiss();
                                      tabLayout.setupWithViewPager(viewPager);
                                  }

                                  @Override
                                  public void onFailure(Call<List<ServiceCenterEntity>> call, Throwable t) {
                                      if (materialDialog != null && materialDialog.isShowing())
                                          materialDialog.dismiss();
                                      MkShop.toast(getActivity(), t.getMessage());

                                  }
                              }

        );


    }


}
