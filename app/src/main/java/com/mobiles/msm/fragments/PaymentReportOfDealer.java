package com.mobiles.msm.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.adapters.DealerReportItemAdapter;
import com.mobiles.msm.adapters.TabsPagerDealerAdapter;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.application.Myenum;
import com.mobiles.msm.pojos.models.Payment;
import com.mobiles.msm.pojos.models.Purchase;

import java.util.List;

public class PaymentReportOfDealer extends Fragment implements TextWatcher {

    private FragmentActivity myContext;
    public static String TAG = "PaymentReportOfDealer";
    TextView totalQuantity, totalRevenue;
    ViewPager viewPager;
    TabsPagerDealerAdapter adapter;
    EditText search;
    Long dealerId;

    public static PaymentReportOfDealer newInstance(Long dealerId) {
        PaymentReportOfDealer fragment = new PaymentReportOfDealer();
        Bundle args = new Bundle();
        if(dealerId!=null)
        args.putLong("dealerName", dealerId);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealerId = getArguments() != null ? getArguments().getLong("dealerName") : null;

    }

    public PaymentReportOfDealer() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyApplication.SCRREN = "PaymentReportOfDealer";

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.dealer_expense_report, container, false);

        init(viewGroup);

        initList();

        search.addTextChangedListener(this);


        return viewGroup;
    }

    private void initList() {

        List<Purchase> purchaseHistories = Purchase.find(Purchase.class, "dealer_id = ?", String.valueOf(dealerId));
        List<Payment> paymentHistories = Payment.find(Payment.class, "dealer_id =?", String.valueOf(dealerId));

        Myenum.INSTANCE.setPurchaseHistories(purchaseHistories);
        Myenum.INSTANCE.setPaymentHistories(paymentHistories);

    }

    private void init(ViewGroup viewGroup) {

        viewPager = (ViewPager) viewGroup.findViewById(R.id.pager);
        totalQuantity = (TextView) viewGroup.findViewById(R.id.totalQuantity);
        totalRevenue = (TextView) viewGroup.findViewById(R.id.totalRevenue);
        search = (EditText) viewGroup.findViewById(R.id.edit_search);
        adapter = new TabsPagerDealerAdapter(myContext.getSupportFragmentManager());
        adapter.setDealerId(dealerId);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) viewGroup.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        int index = viewPager.getCurrentItem();
        TabsPagerDealerAdapter adapter = ((TabsPagerDealerAdapter) viewPager.getAdapter());
        DealerReportViewPagerFragment page = adapter.getFragment(index);
        RecyclerView listView = (RecyclerView) page.getView().findViewById(R.id.recycler_view);
        DealerReportItemAdapter adapters = (DealerReportItemAdapter) listView.getAdapter();
        adapters.setSearchText(s);


    }
}
