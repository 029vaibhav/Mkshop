package com.mobiles.msm.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.IncentiveEntity;
import com.mobiles.msm.pojos.models.Product;
import com.mobiles.msm.pojos.models.ProductTable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 31/7/15.
 */
public class NewIncentiveFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    AutoCompleteTextView brand, model;
    EditText basePrice, quantity, incentive;
    Button submit;
    TextView validity;
    List<Product> salesList;
    List<Product> modelList;
    ProgressDialog materialDialog;
    String validityDate;
    List<Product> sales;

    public static NewIncentiveFragment newInstance(int pos) {
        NewIncentiveFragment fragment = new NewIncentiveFragment();
        return fragment;
    }

    public NewIncentiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_new_incentive, container, false);
        brand = (AutoCompleteTextView) viewGroup.findViewById(R.id.brandtext);
        model = (AutoCompleteTextView) viewGroup.findViewById(R.id.modeltext);
        basePrice = (EditText) viewGroup.findViewById(R.id.basePrice);
        quantity = (EditText) viewGroup.findViewById(R.id.quantity);
        incentive = (EditText) viewGroup.findViewById(R.id.incentiveAmount);
        validity = (TextView) viewGroup.findViewById(R.id.validity);
        submit = (Button) viewGroup.findViewById(R.id.submit);
        salesList = new ArrayList<>();
        modelList = new ArrayList<>();
        materialDialog = NavigationMenuActivity.materialDialog;
        salesList.clear();
        initData();


        return viewGroup;
    }

    private void initData() {

        materialDialog.dismiss();
        salesList = Lists.newArrayList(Iterables.filter(sales, new Predicate<Product>() {
            @Override
            public boolean apply(Product input) {
                return (input.getType().equalsIgnoreCase(ProductType.Mobile.name()));
            }
        }));


        List<String> brandStrings = new ArrayList<String>();
        Set<String> brands = new HashSet();
        for (int i = 0; i < sales.size(); i++) {
            brands.add(sales.get(i).getBrand());
        }
        brandStrings.addAll(brands);

        ArrayAdapter<String> mobilelist = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, brandStrings);
        brand.setThreshold(1);
        brand.setAdapter(mobilelist);


        model.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (brand.getText().length() == 0)
                        MyApplication.toast(getActivity(), "please select brand first");
                    else {

                        modelList = Lists.newArrayList(Iterables.filter(salesList, new Predicate<Product>() {
                            @Override
                            public boolean apply(Product input) {
                                return (input.getBrand().equalsIgnoreCase(brand.getText().toString()));
                            }
                        }));

                        List<String> brandList = new ArrayList<String>();
                        Set<String> brandStrings = new HashSet();
                        for (int i = 0; i < modelList.size(); i++) {
                            brandStrings.add(modelList.get(i).getModel());
                        }
                        brandList.addAll(brandStrings);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity(), android.R.layout.select_dialog_item, brandList);
                        model.setThreshold(1);
                        model.setAdapter(adapter);


                    }
                }
            }
        });


        validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        String sToDate = MyApplication.checkDigit(i3) + "-" + MyApplication.checkDigit(i2 + 1) + "-" + i;
                        validity.setText(sToDate);
                        DateTime dt = new DateTime(i, i2 + 1, i3, 23, 59);
                        validityDate = dt.toString(getString(R.string.date_format));
                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (brand.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please select brand");

                else if (model.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please select model");

                else if (basePrice.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please enter base price");

                else if (quantity.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please mention quantity");

                else if (incentive.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please enter incentive amount");
                else if (validity.getText().length() == 0 || validity.getText().toString().equalsIgnoreCase("Validity"))
                    MyApplication.toast(getActivity(), "please select date");
                else {

                    materialDialog.show();
                    IncentiveEntity incentiveEntity = new IncentiveEntity();
                    incentiveEntity.setBasePrice(basePrice.getText().toString());
                    incentiveEntity.setBrand(brand.getText().toString());
                    incentiveEntity.setModel(model.getText().toString());
                    incentiveEntity.setValidity(validityDate);
                    incentiveEntity.setQuantity(quantity.getText().toString());
                    incentiveEntity.setIncentiveAmount(incentive.getText().toString());
                    Client.INSTANCE.createIncentive(MyApplication.AUTH, incentiveEntity).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MyApplication.toast(getActivity(), "Registered Successfully");

                            Fragment fragment = getFragmentManager().findFragmentByTag(Incentive.TAG);
                            if (fragment == null) {
                                fragment = new Incentive();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MyApplication.toast(getActivity(), t.getMessage());

                        }
                    });

                }


            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProductTable.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        sales = ProductTable.getRows(data, true);
        initData();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
