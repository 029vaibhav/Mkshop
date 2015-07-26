package com.mobiles.mkshop.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiles.mkshop.adapters.SalesReportItemAdapter;
import com.mobiles.mkshop.pojos.ProductType;
import com.mobiles.mkshop.R;


public class SalesReportList extends Fragment {


    ProductType productType;
    int pt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pt = getArguments() != null ? getArguments().getInt("num") : 1;

        if (pt == 0) {
            productType = ProductType.MOBILE;
        } else {
            productType = ProductType.ACCESSORY;
        }

    }

    public static SalesReportList newInstance(int pos) {
        SalesReportList fragment = new SalesReportList();
        Bundle args = new Bundle();
        args.putInt("num", pos);
        fragment.setArguments(args);

        return fragment;
    }

    public SalesReportList() {
        // Required empty public constructor
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sales_report_list, container, false);
        RecyclerView recyclerView= (RecyclerView) viewGroup.findViewById(R.id.saleslistview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        SalesReportItemAdapter listItemAdapter = new SalesReportItemAdapter(this, productType);
        recyclerView.setAdapter(listItemAdapter);


        return viewGroup;
    }


}
