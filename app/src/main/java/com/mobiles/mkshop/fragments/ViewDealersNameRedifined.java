package com.mobiles.mkshop.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.adapters.ViewDealerNameAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.ProductExpense;

import org.joda.time.DateTime;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ViewDealersNameRedifined extends Fragment implements View.OnClickListener {

    String sFromDate, sToDate;
    MaterialDialog materialDialog;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    FloatingActionButton fab;

    public static ViewDealersName newInstance() {
        ViewDealersName fragment = new ViewDealersName();

        return fragment;
    }

    public ViewDealersNameRedifined() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dealer_name, container, false);


        init(viewGroup);


        fab.setOnClickListener(this);

        executeQuery();

        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {


        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        materialDialog = new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();


        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);

        sFromDate = sharedPreferences.getString(MkShop.LAST_VIEWED_DATE, "2015-01-01");
        sToDate = DateTime.now().toString("yyyy-MM-dd");

    }

    private void executeQuery() {

        materialDialog.show();

        Client.INSTANCE.getPurchasedProduct(MkShop.AUTH, sFromDate, sToDate, new Callback<List<ProductExpense>>() {
            @Override
            public void success(final List<ProductExpense> productExpenses, Response response) {

              //  sharedPreferences.edit().putString(MkShop.LAST_VIEWED_DATE, sToDate).commit();
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                for (int i = 0; i < productExpenses.size(); i++) {
                    List<ProductExpense> expenses = ProductExpense.find(ProductExpense.class, "server_id = ?", productExpenses.get(i).getServerId());
                    if (expenses == null || expenses.size() == 0) {
                        productExpenses.get(i).setEntries(productExpenses.get(i).getProductExpenseSingleEntries());
                        productExpenses.get(i).save();
                    }
                }
                List<ProductExpense> productExpense = ProductExpense.listAll(ProductExpense.class);

//                HashMap<String, DealerPaymentInfo> paymentInfoHashMap = new HashMap<>();
//                for (ProductExpense productExpense1 : productExpense) {
//
//                    List<ProductExpenseSingleEntry> productExpenseSingleEntries = productExpense1.getEntries(productExpense1.getServerId());
//
//
//                    if (paymentInfoHashMap.get(productExpense1.getDealerName()) == null) {
//                        int amount = 0;
//                        for (ProductExpenseSingleEntry productExpenseSingleEntry : productExpenseSingleEntries) {
//                            amount = amount + Integer.parseInt(productExpenseSingleEntry.getAmount());
//                        }
//                        paymentInfoHashMap.put(productExpense1.getDealerName(), new DealerPaymentInfo(Integer.parseInt(productExpense1.getTotalAmt()), amount));
//                    } else {
//                        DealerPaymentInfo dealerPaymentInfo = paymentInfoHashMap.get(productExpense1.getDealerName());
//                        int totalAmount = dealerPaymentInfo.getTotalAmount();
//                        int amount = dealerPaymentInfo.getPaidAmount();
//                        for (ProductExpenseSingleEntry productExpenseSingleEntry : productExpenseSingleEntries) {
//                            amount = amount + Integer.parseInt(productExpenseSingleEntry.getAmount());
//                        }
//                        totalAmount = totalAmount + Integer.parseInt(productExpense1.getTotalAmt());
//                        dealerPaymentInfo.setPaidAmount(amount);
//                        dealerPaymentInfo.setTotalAmount(totalAmount);
//                        paymentInfoHashMap.put(productExpense1.getDealerName(), dealerPaymentInfo);
//                    }
//
//
//
//                }
//                List<Map.Entry<String, DealerPaymentInfo>> list = new ArrayList(paymentInfoHashMap.entrySet());

//                Set<String> strings = new HashSet<>();
//                for (ProductExpense abc : productExpense) {
//                    strings.add(abc.getDealerName());
//                }
//                ViewDealerNameAdapter viewBillAdapter = new ViewDealerNameAdapter(ViewDealersName.this, productExpense);
//                recyclerView.setAdapter(viewBillAdapter);

            }

            @Override
            public void failure(RetrofitError error) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                MkShop.toast(getActivity(), error.getMessage().toString());

            }
        });


    }


    @Override
    public void onClick(View v) {

        Fragment fragment = new RegisterProductExpenseFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
