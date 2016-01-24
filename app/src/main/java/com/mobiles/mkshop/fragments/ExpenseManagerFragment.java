package com.mobiles.mkshop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;


public class ExpenseManagerFragment extends Fragment {

    public static String TAG = "ExpenseManagerFragment";


    public static ExpenseManagerFragment newInstance() {
        ExpenseManagerFragment fragment = new ExpenseManagerFragment();

        return fragment;
    }

    public ExpenseManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_expense_manager, container, false);

        //  TextView registerProduct = (TextView) viewGroup.findViewById(R.id.registerProduct);
        TextView paySalary = (TextView) viewGroup.findViewById(R.id.paySalary);
        TextView viewExpenses = (TextView) viewGroup.findViewById(R.id.viewExpenses);
        TextView viewBill = (TextView) viewGroup.findViewById(R.id.viewBills);


        paySalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = UserListFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

            }
        });

        viewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = ViewExpenseFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

            }
        });

        viewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = ViewDealersName.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

            }
        });


        return viewGroup;
    }


}
