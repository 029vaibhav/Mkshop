package com.mobiles.mkshop.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.IncentiveUserlistDialogAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.enums.PaymentType;
import com.mobiles.mkshop.pojos.models.EmployeeExpense;
import com.mobiles.mkshop.pojos.models.Sales;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncentiveSalesUserListFragment extends Fragment {


    static List<Sales> salesList;
    RecyclerView recyclerView;
    EditText amount;
    Button submit;
    ProgressDialog materialDialog;
    String message, id;


    public static IncentiveSalesUserListFragment newInstance(List<Sales> sales, String id, String message) {
        IncentiveSalesUserListFragment fragment = new IncentiveSalesUserListFragment();
        salesList = sales;
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salesList = salesList;
        message = getArguments() != null ? getArguments().getString("message") : "";
        id = getArguments() != null ? getArguments().getString("id") : "";


    }


    public IncentiveSalesUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dialog_incentive_user_sale_list, container, false);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.usersalelist);
        amount = (EditText) viewGroup.findViewById(R.id.amount);
        submit = (Button) viewGroup.findViewById(R.id.submit);
        TextView messageTextView = (TextView) viewGroup.findViewById(R.id.message);

        messageTextView.setText(message);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        IncentiveUserlistDialogAdapter incentiveUserlistDialogAdapter = new IncentiveUserlistDialogAdapter(IncentiveSalesUserListFragment.this, salesList);
        recyclerView.setAdapter(incentiveUserlistDialogAdapter);

        materialDialog = NavigationMenuActivity.materialDialog;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amount.getText().length() != 0) {

                    materialDialog.show();

                    EmployeeExpense employeeExpense = new EmployeeExpense();
                    employeeExpense.setPaymentType(PaymentType.Incentive);
                    employeeExpense.setUsername(salesList.get(0).getUsername());
                    employeeExpense.setAmount(Integer.parseInt(amount.getText().toString()));

                    Call<EmployeeExpense> stringCall = Client.INSTANCE.payUserIncentive(MkShop.AUTH, employeeExpense);
                    stringCall.enqueue(new Callback<EmployeeExpense>() {
                        @Override
                        public void onResponse(Call<EmployeeExpense> call, Response<EmployeeExpense> response) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MkShop.toast(getActivity(), "Success");
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }

                        @Override
                        public void onFailure(Call<EmployeeExpense> call, Throwable t) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();

                            MkShop.toast(getActivity(), t.getMessage());
                        }
                    });

                } else {
                    MkShop.toast(getActivity(), "please enter the incentive amount");
                }

            }
        });

        return viewGroup;
    }


}
