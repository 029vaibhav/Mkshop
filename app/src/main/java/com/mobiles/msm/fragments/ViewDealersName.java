package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.ViewDealerNameAdapter;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.DealerInfo;
import com.mobiles.msm.pojos.models.ExpenseManager;
import com.mobiles.msm.pojos.models.Payment;
import com.mobiles.msm.pojos.models.Purchase;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewDealersName extends Fragment implements View.OnClickListener {

    String sToDate;
    ProgressDialog materialDialog;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    FloatingActionButton fab;

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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dealer_name, container, false);
        init(viewGroup);
        fab.setOnClickListener(this);
        executeQuery();
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {


        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        materialDialog = NavigationMenuActivity.materialDialog;
        sharedPreferences = getActivity().getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        sToDate = sharedPreferences.getString(MyApplication.LAST_VIEWED_DATE, null);
        if (sToDate == null) {
            DateTime dateTime = new DateTime(2015, 1, 1, 0, 0);
            sToDate = dateTime.toString("yyyy-MM-dd'T'HH:mm:ss");
        }

    }

    private void executeQuery() {

        materialDialog.show();

        Client.INSTANCE.getPurchasedProduct(MyApplication.AUTH, sToDate).enqueue(new Callback<List<ExpenseManager>>() {
            @Override
            public void onResponse(Call<List<ExpenseManager>> call, Response<List<ExpenseManager>> response) {

                DateTime dateTime = new DateTime(DateTimeZone.UTC);
                String currentDateTime = dateTime.minusSeconds(30).toString("yyyy-MM-dd'T'HH:mm:ss");
                sharedPreferences.edit().putString(MyApplication.LAST_VIEWED_DATE, currentDateTime).apply();
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                List<ExpenseManager> serverExpenseManagers = response.body();
                for (int i = 0; i < serverExpenseManagers.size(); i++) {
                    DealerInfo dealerInfo = serverExpenseManagers.get(i).getDealerInfo();
                    List<DealerInfo> dealerInfos = DealerInfo.find(DealerInfo.class, "server_id = ? ", String.valueOf(dealerInfo.getServerId()));
                    if (dealerInfos == null || dealerInfos.size() == 0)
                        dealerInfo.save();
                    else {
                        DealerInfo dealerInfo1 = dealerInfos.get(0);
                        dealerInfo1.setTotalAmount(serverExpenseManagers.get(i).getDealerInfo().getTotalAmount());
                        dealerInfo1.setPayedAmount(serverExpenseManagers.get(i).getDealerInfo().getPayedAmount());
                        dealerInfo1.save();
                    }
                    final List<Payment> paymentList = serverExpenseManagers.get(i).getPaymentList();
                    final List<Purchase> purchaseList = serverExpenseManagers.get(i).getPurchaseList();
                    ExecutorService executorService = Executors.newCachedThreadPool();
                    List<Callable<List<Void>>> callableList = new ArrayList<>();
                    savePaymentAndPurchaseInBackGround(callableList, paymentList, purchaseList);
                    try {
                        List<Future<List<Void>>> futures = executorService.invokeAll(callableList);
                        executorService.shutdown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                List<ExpenseManager> expenseManagerList = new ArrayList<ExpenseManager>();
                Iterator<DealerInfo> all = DealerInfo.findAll(DealerInfo.class);
                while (all.hasNext()) {
                    ExpenseManager expenseManager = new ExpenseManager();
                    DealerInfo dealerInfo = all.next();
                    List<Purchase> purchases = Purchase.find(Purchase.class, "dealer_id = ?", String.valueOf(dealerInfo.getServerId()));
                    List<Payment> payments = Payment.find(Payment.class, "dealer_id = ?", String.valueOf(dealerInfo.getServerId()));
                    expenseManager.setDealerInfo(dealerInfo);
                    expenseManager.setPaymentList(payments);
                    expenseManager.setPurchaseList(purchases);
                    expenseManagerList.add(expenseManager);
                }


                ViewDealerNameAdapter viewBillAdapter = new ViewDealerNameAdapter(ViewDealersName.this, expenseManagerList);
                recyclerView.setAdapter(viewBillAdapter);

            }

            @Override
            public void onFailure(Call<List<ExpenseManager>> call, Throwable t) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                    MyApplication.toast(getActivity(), t.getMessage().toString());

            }
        });


    }

    private void savePaymentAndPurchaseInBackGround(List<Callable<List<Void>>> callableList, final List<Payment> paymentList, final List<Purchase> purchaseList) {

        if (paymentList.size() > 0) {
            callableList.add(new Callable<List<Void>>() {
                @Override
                public List<Void> call() throws Exception {
                    for (Payment payment : paymentList) {
                        List<Payment> paymentList1 = Payment.find(Payment.class, "server_id = ?", String.valueOf(payment.getServerId()));
                        if (paymentList1 == null || paymentList1.size() == 0) {
                            Payment payment1 = new Payment();
                            payment1.setAmount(payment.getAmount());
                            payment1.setNote(payment.getNote());
                            payment1.setServerId(payment.getServerId());
                            payment1.setCreated(payment.getCreated());
                            payment1.setDealerId(payment.getDealerId());
                            payment1.setModified(payment.getModified());
                            payment1.save();
                        }
                    }
                    return null;
                }
            });
        }

        if (purchaseList.size() > 0) {
            callableList.add(new Callable<List<Void>>() {
                @Override
                public List<Void> call() throws Exception {
                    for (Purchase purchase : purchaseList) {
                        List<Purchase> purchases = Purchase.find(Purchase.class, "server_id = ?", String.valueOf(purchase.getServerId()));
                        if (purchases == null || purchases.size() == 0) {
                            Purchase purchase1 = new Purchase();
                            purchase1.setAmount(purchase.getAmount());
                            purchase1.setModified(purchase.getModified());
                            purchase1.setDealerId(purchase.getDealerId());
                            purchase1.setCreated(purchase.getCreated());
                            purchase1.setImage(purchase.getImage());
                            purchase1.setServerId(purchase.getServerId());
                            purchase1.setNote(purchase.getNote());
                            purchase1.save();
                        }
                    }
                    return null;
                }
            });
        }

    }


    @Override
    public void onClick(View v) {

        showDialog();
//        Fragment fragment = CreateNewTransaction.newInstance(null);
//        getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }

    private void showDialog() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("New Dealer");
        alertDialog.setMessage("Enter Dealer name");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int which) {

                        if (input.getText().length() <= 0) {
                            MyApplication.toast(getActivity(), "please enter dealer name");
                        } else {
                            final DealerInfo dealerInfo = new DealerInfo();
                            dealerInfo.setDealerName(input.getText().toString());
                            Client.INSTANCE.registerDealerInfo(MyApplication.AUTH, dealerInfo).enqueue(new Callback<DealerInfo>() {
                                @Override
                                public void onResponse(Call<DealerInfo> call, Response<DealerInfo> response) {
                                    dealerInfo.setServerId(response.body().getServerId());
                                    dealerInfo.save();
                                    dialog.dismiss();
                                    MyApplication.toast(getActivity(), "successfully registered");
                                }

                                @Override
                                public void onFailure(Call<DealerInfo> call, Throwable t) {
                                    dialog.dismiss();
                                        MyApplication.toast(getActivity(), t.getMessage());
                                }
                            });

                        }

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
