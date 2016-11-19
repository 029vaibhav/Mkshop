package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.adapters.ViewProductadapter;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.contentprovider.ProductHelper;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ViewProductFragment extends Fragment {

    public static String TAG = "ViewProductFragment";

    ProgressDialog dialog;
    AutoCompleteTextView brandTextView, search;
    TextView submit;
    RecyclerView gridRecyclerView;
    ScrollView scrollView;
    List<Product> salesList;
    FloatingActionButton floatingActionButton;
    FloatingActionButton accessoryFab;


    public static ViewProductFragment newInstance() {
        ViewProductFragment fragment = new ViewProductFragment();

        return fragment;
    }

    public ViewProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_view_product, container, false);

        salesList = new ArrayList<>();
        brandTextView = (AutoCompleteTextView) viewGroup.findViewById(R.id.brand);
        scrollView = (ScrollView) viewGroup.findViewById(R.id.scroll_view);
        gridRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) viewGroup.findViewById(R.id.fab);
        accessoryFab = (FloatingActionButton) viewGroup.findViewById(R.id.accessory_fab);


        search = (AutoCompleteTextView) viewGroup.findViewById(R.id.search);
        submit = (TextView) viewGroup.findViewById(R.id.submit);
        dialog = NavigationMenuActivity.materialDialog;
        dialog.show();

        List<Product> sales = ProductHelper.getAllProducts(getActivity().getContentResolver());
        salesList = Lists.newArrayList(Iterables.filter(sales, new Predicate<Product>() {
            @Override
            public boolean apply(Product input) {
                return (input.getType().equals(ProductType.Mobile));
            }
        }));

        List<String> brandStrings = new ArrayList<String>();
        Set<String> brand = new HashSet();
        for (int i = 0; i < sales.size(); i++) {
            brand.add(sales.get(i).getBrand().trim());
        }
        brandStrings.addAll(brand);

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, brandStrings);
        brandTextView.setThreshold(1);
        brandTextView.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brandTextView.getText().length() <= 0) {
                    MyApplication.toast(getActivity(), "please select brand");
                } else {

                    dialog.show();

                    List<Product> modelListByBrand = Lists.newArrayList(Iterables.filter(salesList, new Predicate<Product>() {
                        @Override
                        public boolean apply(Product input) {
                            return (input.getBrand().equalsIgnoreCase(brandTextView.getText().toString()));
                        }
                    }));
                    scrollView.setVisibility(View.GONE);
                    floatingActionButton.setVisibility(View.GONE);
                    accessoryFab.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    gridRecyclerView.setVisibility(View.VISIBLE);
                    gridRecyclerView.setHasFixedSize(true);
                    gridRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    final ViewProductadapter listItemAdapter = new ViewProductadapter(ViewProductFragment.this, getActivity(), modelListByBrand);
                    gridRecyclerView.setAdapter(listItemAdapter);
                    (brandTextView).setInputType(0);


                    dialog.dismiss();
                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (s != null)
                                listItemAdapter.filter(s);
                        }
                    });


                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewProductFragment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });

        accessoryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NewAccessoryFargment();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });

        return viewGroup;
    }


}
