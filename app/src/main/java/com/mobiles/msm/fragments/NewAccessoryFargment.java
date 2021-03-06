package com.mobiles.msm.fragments;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.contentprovider.ProductHelper;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.Product;
import com.mobiles.msm.pojos.models.ProductTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewAccessoryFargment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAccessoryFargment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Product> products;
    private boolean synchronizedComp = false;

    AppCompatAutoCompleteTextView brandEditText, modelEditText, accessoryTypeEditText;
    EditText priceEditText;
    Button submit;
    ProgressDialog progressDialog;
    ViewGroup viewGroup;


    public NewAccessoryFargment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAccessoryFargment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAccessoryFargment newInstance(String param1, String param2) {
        NewAccessoryFargment fragment = new NewAccessoryFargment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_new_accessory, container, false);
        initView(viewGroup);
        progressDialog.show();
        return viewGroup;
    }


    private void initData() {
        progressDialog.dismiss();
        Set<String> brand = new HashSet<>(), model = new HashSet<>(), accessoryType = new HashSet<>();
        for (Product product : products) {
            brand.add(product.getBrand());
            model.add(product.getModel());
            accessoryType.add(product.getAccessoryType());
        }
        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(brand));
        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(model));
        ArrayAdapter<String> accessoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(accessoryType));
        brandEditText.setAdapter(brandAdapter);
        modelEditText.setAdapter(modelAdapter);
        accessoryTypeEditText.setAdapter(accessoryAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateAccessory();
            }
        });


    }

    private void initView(ViewGroup viewGroup) {

        brandEditText = (AppCompatAutoCompleteTextView) viewGroup.findViewById(R.id.brand);
        brandEditText.setThreshold(1);
        modelEditText = (AppCompatAutoCompleteTextView) viewGroup.findViewById(R.id.model);
        modelEditText.setThreshold(1);
        accessoryTypeEditText = (AppCompatAutoCompleteTextView) viewGroup.findViewById(R.id.accessory_type);
        accessoryTypeEditText.setThreshold(1);
        priceEditText = (EditText) viewGroup.findViewById(R.id.price);
        submit = (Button) viewGroup.findViewById(R.id.form_submit);
        progressDialog = NavigationMenuActivity.materialDialog;


    }

    private void validateAccessory() {

        if (brandEditText.getText() == null || brandEditText.getText().length() == 0) {
            MyApplication.toast(getActivity(), "please enter brand");
        } else if (modelEditText.getText() == null || modelEditText.getText().length() == 0) {
            MyApplication.toast(getActivity(), "please enter model");
        } else if (accessoryTypeEditText.getText() == null || accessoryTypeEditText.getText().length() == 0) {
            MyApplication.toast(getActivity(), "please enter accessory type");
        } else if (priceEditText.getText() == null || priceEditText.getText().length() == 0) {
            MyApplication.toast(getActivity(), "please enter price");
        } else {
            progressDialog.show();
            final Product product = new Product();
            product.setType(ProductType.Accessory.name());
            product.setAccessoryType(accessoryTypeEditText.getText().toString());
            product.setModel(modelEditText.getText().toString());
            product.setBrand(brandEditText.getText().toString());
            product.setPrice(Integer.parseInt(priceEditText.getText().toString()));
            Call<Product> product1 = Client.INSTANCE.createProduct(MyApplication.AUTH, product);
            product1.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        MyApplication.toast(getActivity(), "Successfully registered a new Accessory");
                        ProductHelper.createProduct(getActivity().getContentResolver(), product);
                    } else {
                        MyApplication.toast(getActivity(), "something went wrong please contact admin");
                    }

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (t.getMessage() != null)
                        MyApplication.toast(getActivity(), t.getMessage());
                    else MyApplication.toast(getActivity(), "something went wrong");
                }
            });


        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ProductTable.CONTENT_URI,
                null,
                ProductTable.FIELD_TYPE + " = ?",
                new String[]{ProductType.Accessory.name()},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        products = ProductTable.getRows(data, true);
        initData();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        synchronizedComp = false;
    }
}
