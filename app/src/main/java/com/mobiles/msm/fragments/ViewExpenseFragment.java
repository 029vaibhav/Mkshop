package com.mobiles.msm.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.TabsPagerAdapterExpense;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.application.Myenum;
import com.mobiles.msm.pojos.enums.PaymentType;
import com.mobiles.msm.pojos.models.EmployeeExpense;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewExpenseFragment extends Fragment {

    private FragmentActivity myContext;
    public static String TAG = "ViewExpenseFragment";
    TextView toDate, fromDate, totalQuantity, totalRevenue;
    ViewPager viewPager;
    TabsPagerAdapterExpense adapter;
    String sFromdate, sToDate;
    int tempQuantity = 0, tempRevenue = 0;
    ProgressDialog materialDialog;

    public static ViewExpenseFragment newInstance() {
        ViewExpenseFragment fragment = new ViewExpenseFragment();
        return fragment;

    }

    public ViewExpenseFragment() {
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
        MyApplication.SCRREN = "ViewExpenseFragment";


        materialDialog = NavigationMenuActivity.materialDialog;

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_view_expense, container, false);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.pager);

        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
        toDate = (TextView) viewGroup.findViewById(R.id.toDate);

        totalQuantity = (TextView) viewGroup.findViewById(R.id.totalQuantity);
        totalRevenue = (TextView) viewGroup.findViewById(R.id.totalRevenue);


        adapter = new TabsPagerAdapterExpense(myContext.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) viewGroup.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                List<EmployeeExpense> employeeExpenses = null;
                tempQuantity = 0;
                tempRevenue = 0;

                if (position == 0) {
                    employeeExpenses = Myenum.INSTANCE.getExpenseList(PaymentType.Salary);
                } else if (position == 1) {
                    employeeExpenses = Myenum.INSTANCE.getExpenseList(PaymentType.Incentive);
                }
                if (employeeExpenses != null) {
                    for (EmployeeExpense employeeExpense : employeeExpenses) {
                        tempRevenue = tempRevenue + employeeExpense.getAmount();
                    }
                    totalRevenue.setText("" + tempRevenue);
                    totalQuantity.setText("");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (fromDate.getText().toString().equalsIgnoreCase("from")) {
                    MyApplication.toast(getActivity(), "please select starting date");
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {


                            String date = MyApplication.checkDigit(i3) + "-" + MyApplication.checkDigit(i2 + 1) + "-" + i;


                            toDate.setText(date);
                            DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);

                            sToDate = dt.toString("yyyy-MM-dd");

                            executeQuery();


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


                        String date = MyApplication.checkDigit(i3) + "-" + MyApplication.checkDigit(i2 + 1) + "-" + i;


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

    private void executeQuery() {

        materialDialog.show();

        Client.INSTANCE.getExpenseReport(MyApplication.AUTH, sFromdate, sToDate).enqueue(new Callback<List<EmployeeExpense>>() {
            @Override
            public void onResponse(Call<List<EmployeeExpense>> call, Response<List<EmployeeExpense>> response) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                Myenum.INSTANCE.setExpenseList(response.body());
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                adapter = new TabsPagerAdapterExpense(myContext.getSupportFragmentManager());
                viewPager.setAdapter(adapter);
                tempQuantity = 0;
                tempRevenue = 0;
                totalRevenue.setText("" + tempRevenue);
                totalQuantity.setText("" + tempQuantity);
            }

            @Override
            public void onFailure(Call<List<EmployeeExpense>> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                MyApplication.toast(getActivity(), t.getMessage().toString());

            }
        });


    }


}
