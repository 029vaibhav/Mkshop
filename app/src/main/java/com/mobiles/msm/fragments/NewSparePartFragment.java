package com.mobiles.msm.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Product;
import com.mobiles.msm.pojos.models.SparePart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewSparePartFragment extends Fragment {


    public static String TAG = "RepairNewItemFragment";
    EditText quantityEditText, descriptionEditText;
    MultiAutoCompleteTextView compatibleModelEditText;
    AutoCompleteTextView typeEditText, brandEditText;
    Button submit;
    ProgressDialog dialog;


    public static NewSparePartFragment newInstance() {
        NewSparePartFragment fragment = new NewSparePartFragment();
        return fragment;
    }

    public NewSparePartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MyApplication.SCRREN = "PartsRequestNewItemFragment";
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.request_part_new_item, container, false);
        dialog = NavigationMenuActivity.materialDialog;
        init(v);

        return v;
    }

    private void init(ViewGroup v) {


        Set<String> strings = new HashSet<>();
        Iterator<SparePart> all = SparePart.findAll(SparePart.class);
        while (all.hasNext()) {
            SparePart next = all.next();
            strings.add(next.getBrand());
            strings.add(next.getType());
        }

        Set<String> brands = new HashSet<>();
        Iterator<Product> all1 = Product.findAll(Product.class);
        while (all1.hasNext()) {
            Product next = all1.next();
            brands.add(next.getBrand());
        }
        final ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(brands));
        ArrayAdapter<String> keywords = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>(strings));

        brandEditText = (AutoCompleteTextView) v.findViewById(R.id.brand);
        brandEditText.setThreshold(1);
        brandEditText.setAdapter(keywords);
        typeEditText = (AutoCompleteTextView) v.findViewById(R.id.type);
        typeEditText.setThreshold(1);
        typeEditText.setAdapter(keywords);
        compatibleModelEditText = (MultiAutoCompleteTextView) v.findViewById(R.id.compatible_model);
        compatibleModelEditText.setThreshold(1);
        compatibleModelEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        compatibleModelEditText.setAdapter(brandAdapter);
        quantityEditText = (EditText) v.findViewById(R.id.quantity);
        descriptionEditText = (EditText) v.findViewById(R.id.description);
        submit = (Button) v.findViewById(R.id.submit);
        dialog = NavigationMenuActivity.materialDialog;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (brandEditText.getText() == null || brandEditText.getText().length() == 0) {
                    MyApplication.toast(getActivity(), "Brand cannot be empty");
                } else if (typeEditText.getText() == null || typeEditText.getText().length() == 0) {
                    MyApplication.toast(getActivity(), "SPare part Type cannot be empty");
                } else if (compatibleModelEditText.getText() == null || compatibleModelEditText.getText().length() == 0) {
                    MyApplication.toast(getActivity(), "compatible model cannot be empty");
                } else if (quantityEditText.getText() == null || quantityEditText.getText().length() == 0) {
                    MyApplication.toast(getActivity(), "quantity cannot be empty");
                } else {
                    SparePart sparePart = new SparePart();
                    sparePart.setBrand(brandEditText.getText().toString());
                    sparePart.setCompatibleMobile(compatibleModelEditText.getText().toString());
                    sparePart.setType(typeEditText.getText().toString());
                    sparePart.setQuantity(Integer.parseInt(quantityEditText.getText().toString()));
                    if (descriptionEditText.getText() != null)
                        sparePart.setDescription(descriptionEditText.getText().toString());
                    sendDataToServer(sparePart);


                }

            }
        });

    }

    private void sendDataToServer(SparePart sparePart) {
        if (dialog != null)
            dialog.show();
        Client.INSTANCE.createSparePart(MyApplication.AUTH, sparePart).enqueue(new Callback<SparePart>() {
            @Override
            public void onResponse(Call<SparePart> call, Response<SparePart> response) {
                Toast.makeText(getActivity(), "success ", Toast.LENGTH_SHORT).show();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(SparePartFragment.TAG);
                if (fragment == null) {
                    fragment = SparePartFragment.newInstance();
                }
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }

            @Override
            public void onFailure(Call<SparePart> call, Throwable t) {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
