package com.mobiles.mkshop.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.Product;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepairNewItemFragment extends Fragment {

    public static String TAG = "RepairNewItemFragment";
    EditText price, other, problem;
    Button submit;
    String Stringdate = "", stringModel, stringBrand, stringStatus;
    int index;
    DatePickerDialog datePickerDialog;
    EditText jobNo;
    //  private RadioGroup radiogroup;
    private TextView status;
    //    TextView date
    AutoCompleteTextView brand, modelNo;
    //    TextView dateTitle;
    List<Product> salesList, modelSalesList;
    List<String> brandList;

    ProgressDialog materialDialog;


    public RepairNewItemFragment() {
        // Required empty public constructor
    }

    public static RepairNewItemFragment newInstance() {
        RepairNewItemFragment fragment = new RepairNewItemFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "RepairNewItemFragment";
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_repair_new_item, container, false);
        init(v);
        modelNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (brand.getText().length() == 0)
                        MkShop.toast(getActivity(), "please select brand first");
                    else {
                        brandList.clear();
                        modelSalesList = Lists.newArrayList(Iterables.filter(salesList, new Predicate<Product>() {
                            @Override
                            public boolean apply(Product input) {
                                return (input.getBrand().equalsIgnoreCase(brand.getText().toString()));
                            }
                        }));


                        Set<String> brandStrings = new HashSet();
                        for (int i = 0; i < modelSalesList.size(); i++) {
                            brandStrings.add(modelSalesList.get(i).getModel());
                        }
                        brandList.addAll(brandStrings);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity(), android.R.layout.select_dialog_item, brandList);
                        modelNo.setThreshold(1);
                        modelNo.setAdapter(adapter);


                    }
                }
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> statusOfParts = Arrays.asList(getResources().getStringArray(R.array.items));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final ArrayAdapter<String> aa1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, statusOfParts);
                builder.setSingleChoiceItems(aa1, index, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        dialog.dismiss();
                        String text = statusOfParts.get(item);
                        if (text != null) {
                            stringStatus = text;
                            if (stringStatus.equalsIgnoreCase("Returned")) {
                                price.setVisibility(View.GONE);
                                price.setText("0");
                            } else {
                                price.setVisibility(View.VISIBLE);
                            }
                            status.setText(stringStatus);
                            setindex(stringStatus);
                        }
                    }

                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }


        });


        return v;
    }

    private void validate() {
        if (brand.getText().length() == 0) {
            MkShop.toast(getActivity(), "please select brand");
        } else if (modelNo.getText().toString().length() == 0) {
            MkShop.toast(getActivity(), "please select model");
        } else if (price.getText().length() <= 0) {
            MkShop.toast(getActivity(), "please enter price");
        } else {
            ServiceCenterEntity service = new ServiceCenterEntity();
            service.setBrand(brand.getText().toString().trim());
            service.setModel(modelNo.getText().toString().trim());
            service.setStatus(stringStatus);
            if (price.getText().length() == 0)
                service.setPrice(0);
            else
                service.setPrice(Integer.parseInt(price.getText().toString()));
            service.setJobNo("" + jobNo.getText().toString().trim());
            service.setDeliveryDate("");

            if (MkShop.Role.equalsIgnoreCase(UserType.RECEPTIONIST.name()) || MkShop.Role.equalsIgnoreCase(UserType.SALESMAN.name())) {
                service.setPlace("SP");
            } else if (MkShop.Role.equalsIgnoreCase(UserType.TECHNICIAN.name())) {
                service.setPlace("SC");
            }
            service.setProblem(problem.getText().toString());
            service.setUsername(MkShop.Username);
            SendData(service);
        }
    }

    private void init(ViewGroup v) {

        materialDialog = NavigationMenuActivity.materialDialog;
        brand = (AutoCompleteTextView) v.findViewById(R.id.brandtext);
        status = (TextView) v.findViewById(R.id.status);
        modelNo = (AutoCompleteTextView) v.findViewById(R.id.modeltext);
        price = (EditText) v.findViewById(R.id.priceEdit);
        other = (EditText) v.findViewById(R.id.otheredit);
        jobNo = (EditText) v.findViewById(R.id.jobnoedit);
        problem = (EditText) v.findViewById(R.id.problemedit);
        submit = (Button) v.findViewById(R.id.submit);

        modelSalesList = new ArrayList<>();
        brandList = new ArrayList<>();
        salesList = new ArrayList<>();
        salesList = Product.listAll(Product.class);
        List<Product> sales = Product.listAll(Product.class);
        brandList.clear();
        Set<String> brandStrings = new HashSet();
        for (int i = 0; i < sales.size(); i++) {
            brandStrings.add(sales.get(i).getBrand());
        }
        brandList.addAll(brandStrings);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, brandList);
        brand.setThreshold(1);
        brand.setAdapter(adapter);

    }

    private int setindex(String status) {


        if (status.equalsIgnoreCase("Pending")) {
            index = 0;
        } else if (status.equalsIgnoreCase("Processing")) {
            index = 1;
        } else if (status.equalsIgnoreCase("Pna")) {

            index = 2;
        } else if (status.equalsIgnoreCase("Done")) {
            index = 3;
        } else if (status.equalsIgnoreCase("Return")) {
            index = 4;
        } else if (status.equalsIgnoreCase("Delivered")) {
            index = 5;
        } else if (status.equalsIgnoreCase("Returned")) {
            index = 6;
        }
        return index;

    }

    private void SendData(ServiceCenterEntity service) {

        Client.INSTANCE.sendService(MkShop.AUTH, service).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                MkShop.toast(getActivity(), "success");
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                materialDialog.dismiss();
                Fragment fragment = new RequestRepair();
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                MkShop.toast(getActivity(), t.getMessage());
            }
        });
    }
}
