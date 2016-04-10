package com.mobiles.mkshop.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.adapters.DealerReportItemAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.enums.TransactionType;
import com.mobiles.mkshop.pojos.models.DealerInfo;
import com.mobiles.mkshop.pojos.models.Payment;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DealerReportViewPagerFragment extends Fragment implements View.OnClickListener {


    public static String TAG = "DealerReportViewPagerFragment";

    TransactionType transactionType;
    int pt;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DealerReportItemAdapter listItemAdapter;
    Long dealerId;
    Dialog dialog;
    ProgressDialog progressMaterialDialog;
    EditText dealerNameEditText, note, amount;
    Button submit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pt = getArguments() != null ? getArguments().getInt("num") : 1;

        if (pt == 0) {
            transactionType = TransactionType.Purchase;
        } else {
            transactionType = TransactionType.Payment;
        }

        dealerId = getArguments() != null ? getArguments().getLong("dealerName") : null;


    }

    public static DealerReportViewPagerFragment newInstance(int pos, Long dealerId) {
        DealerReportViewPagerFragment fragment = new DealerReportViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("num", pos);
        if (dealerId != null)
            args.putLong("dealerName", dealerId);
        fragment.setArguments(args);

        return fragment;
    }

    public DealerReportViewPagerFragment() {
        // Required empty public constructor
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dealer_name, container, false);

        init(viewGroup);

        recyclerView.setLayoutManager(linearLayoutManager);
        listItemAdapter = new DealerReportItemAdapter(this, transactionType);
        recyclerView.setAdapter(listItemAdapter);


        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recycler_view);
        fab = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        fab.setOnClickListener(this);


        linearLayoutManager = new LinearLayoutManager(getActivity());


        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dealer_pay_due_amount);


        dealerNameEditText = (EditText) dialog.findViewById(R.id.dealer_edit);
        dealerNameEditText.setEnabled(false);
        amount = (EditText) dialog.findViewById(R.id.total_edit);
        note = (EditText) dialog.findViewById(R.id.note);
        submit = (Button) dialog.findViewById(R.id.submit);
        dialogSubmitRules();
        progressMaterialDialog = NavigationMenuActivity.materialDialog;

    }


    @Override
    public void onClick(View v) {

        if (transactionType == TransactionType.Purchase) {
            Fragment fragment = CreateNewTransaction.newInstance(dealerId);
            getFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        } else {
            dialog.show();
            List<DealerInfo> dealerInfos = DealerInfo.find(DealerInfo.class, "server_id = ? ", String.valueOf(dealerId));

            if (dealerInfos.size() > 0) {
                DealerInfo dealerInfo = dealerInfos.get(0);
                dealerNameEditText.setText(dealerInfo.getDealerName());
            }

        }

    }

    private void dialogSubmitRules() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amount.getText().length() == 0) {
                    MkShop.toast(getActivity(), "amount cant be blank");
                } else if (note.getText().length() == 0) {
                    MkShop.toast(getActivity(), "note cant be blank");
                } else {

                    Payment payment = new Payment();
                    payment.setAmount(Integer.parseInt(amount.getText().toString()));
                    payment.setNote(note.getText().toString());
                    payment.setDealerId(dealerId);
                    sendDataToServer(payment);

                }
            }
        });
    }

    private void sendDataToServer(Payment payment) {

        progressMaterialDialog.show();

        Call<Payment> paymentCall = Client.INSTANCE.duePayment(MkShop.AUTH, payment);
        paymentCall.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();

                if (progressMaterialDialog != null && progressMaterialDialog.isShowing())
                    progressMaterialDialog.dismiss();
                MkShop.toast(getActivity(), "payment successful");
                int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
                for (int i = 0; i < backStackEntryCount; i++) {
                    getFragmentManager().popBackStack();
                }
                Fragment fragment = ExpenseManagerFragment.newInstance();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                if (progressMaterialDialog != null && progressMaterialDialog.isShowing())
                    progressMaterialDialog.dismiss();

                    MkShop.toast(getActivity(), t.getMessage());

            }
        });
    }
}
