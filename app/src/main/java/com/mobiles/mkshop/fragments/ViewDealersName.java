package com.mobiles.mkshop.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.DealerAccount;
import com.mobiles.mkshop.pojos.ProductExpense;

import java.util.ArrayList;
import java.util.List;


public class ViewDealersName extends Fragment {

    TextView totalQuantity, totalRevenue;
    //            toDate, fromDate,
    String sFromdate, sToDate;
    MaterialDialog materialDialog;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String lastModifiedDate;

    public static ViewDealersName newInstance() {
        ViewDealersName fragment = new ViewDealersName();

        return fragment;
    }

    public ViewDealersName() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_view_bills, container, false);

//        fromDate = (TextView) viewGroup.findViewById(R.id.fromDate);
//        toDate = (TextView) viewGroup.findViewById(R.id.toDate);
        sharedPreferences = this.getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        lastModifiedDate = sharedPreferences.getString(MkShop.LAST_MODIFIED_DATE, "2015-01-01");


        totalQuantity = (TextView) viewGroup.findViewById(R.id.totalQuantity);
        totalRevenue = (TextView) viewGroup.findViewById(R.id.totalRevenue);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();
        executeQuery();


//        toDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if (fromDate.getText().toString().equalsIgnoreCase("from")) {
//                    MkShop.toast(getActivity(), "please select starting date");
//                } else {
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
//
//                            if (datePicker.isShown()) {
//                                String date = MkShop.checkDigit(i3) + "-" + MkShop.checkDigit(i2 + 1) + "-" + i;
//
//
//                                toDate.setText(date);
//                                DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);
//
//                                sToDate = dt.toString("yyyy-MM-dd");
//
//                                executeQuery();
//                            }
//
//
//                        }
//                    }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
//                    datePickerDialog.show();
//                }
//            }
//        });


//        fromDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
//
//
//                        String date = MkShop.checkDigit(i3) + "-" + MkShop.checkDigit(i2 + 1) + "-" + i;
//
//
//                        fromDate.setText(date);
//                        DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);
//                        sFromdate = dt.toString("yyyy-MM-dd");
//                    }
//                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
//                datePickerDialog.show();
//            }
//        });


        return viewGroup;
    }

    private void executeQuery() {

        materialDialog.show();


//        Client.INSTANCE.getPurchasedProduct(MkShop.AUTH, sFromdate, lastModifiedDate, new Callback<List<DealerAccount>>() {
//            @Override
//            public void success(final List<DealerAccount> dealerAccountList, Response response) {

        List<DealerAccount> dealerAccountList = new ArrayList<>();

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();


                List<DealerAccount> localDealerAccounts = DealerAccount.listAll(DealerAccount.class);
                if (localDealerAccounts == null) {

                    for (int i = 0; i < dealerAccountList.size(); i++) {
                        DealerAccount newDealerAccount = dealerAccountList.get(i);
                        newDealerAccount.save();
                    }

                } else {


                    dealerAccountList.removeAll(localDealerAccounts);

                    for (int i = 0; i < dealerAccountList.size(); i++) {
                        DealerAccount dealerAccount = dealerAccountList.get(i);
                        dealerAccount.save();
                    }

                    localDealerAccounts = DealerAccount.listAll(DealerAccount.class);

                    for (int k = 0; k < dealerAccountList.size(); k++) {

                        DealerAccount dealerAccount = dealerAccountList.get(k);
                        List<ProductExpense> productExpenses = dealerAccount.getProductExpenses();


                        DealerAccount dealerAccount1 = DealerAccount.find(DealerAccount.class, "dealer_id =?", dealerAccount.getDealerId()).get(0);
                        productExpenses.removeAll(dealerAccount1.getProductExpenses());

                        dealerAccount1.getProductExpenses().addAll(productExpenses);
                        dealerAccount1.save();

                    }

                }
                localDealerAccounts = DealerAccount.listAll(DealerAccount.class);
//
//                ViewDealerNameAdapter viewBillAdapter = new ViewDealerNameAdapter(ViewDealersName.this, localDealerAccounts);
//                recyclerView.setAdapter(viewBillAdapter);


//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                if (materialDialog != null && materialDialog.isShowing())
//                    materialDialog.dismiss();
//                MkShop.toast(getActivity(), error.getMessage().toString());
//
//            }
//        });


    }


}
