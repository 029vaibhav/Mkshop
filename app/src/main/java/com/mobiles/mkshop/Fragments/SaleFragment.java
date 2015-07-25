package com.mobiles.mkshop.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.Adapters.CustomAdapter;
import com.mobiles.mkshop.Application.Client;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Pojos.ProductType;
import com.mobiles.mkshop.Pojos.Sales;
import com.mobiles.mkshop.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.mobiles.mkshop.Application.MkShop.toast;


public class SaleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public static String TAG = "Sales";


    private RadioGroup radiogroup;
    private TextView brand, accessoryType, modelNo;
    EditText quantity, price, other;
    Button submit;
    List<Sales> salesList,modelSalesList;
    List<String> brandList, modelList;


    String stringBrand = null, stringProductType = "mobile", stringAccessory = null, stringModel = null;


    public static SaleFragment newInstance(String brand, String model) {
        SaleFragment fragment = new SaleFragment();
        Bundle args = new Bundle();
        args.putString("brand", brand);
        args.putString("model", model);
        fragment.setArguments(args);

        return fragment;
    }

    public SaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stringBrand = getArguments().getString("brand");
            stringModel = getArguments().getString("model");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MkShop.SCRREN = "SaleFragment";
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_sale, container, false);

        radiogroup = (RadioGroup) v.findViewById(R.id.radiogroup);
        brand = (TextView) v.findViewById(R.id.brandtext);
        accessoryType = (TextView) v.findViewById(R.id.accessoryType);
        modelNo = (TextView) v.findViewById(R.id.modeltext);
        quantity = (EditText) v.findViewById(R.id.quantityEdit);
        price = (EditText) v.findViewById(R.id.priceEdit);
        other = (EditText) v.findViewById(R.id.otheredit);
        submit = (Button) v.findViewById(R.id.submit);


        if (getArguments() != null) {
            brand.setText(stringBrand);
            modelNo.setText(stringModel);
        }

        brandList = new ArrayList<>();

        Client.INSTANCE.getproduct(new Callback<List<Sales>>() {
            @Override
            public void success(List<Sales> sales, Response response) {


                salesList = sales;
                Set<String> brand = new HashSet();
                for (int i = 0; i < sales.size(); i++) {
                    brand.add(sales.get(i).getBrand());
                }
                brandList.addAll(brand);


            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final List<String> brandList = new ArrayList();
//                brandList.add("sony");
//                brandList.add("micromax");
//                brandList.add("lenovo");
//                brandList.add("htc");


                final Dialog view = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_NoActionBar);
                view.setContentView(R.layout.dialog_layout);

                TextView title = (TextView) view.findViewById(R.id.dialogtitle);
                title.setText("Brand");
                ListView listView = (ListView) view.findViewById(R.id.dialoglist);
                CustomAdapter customAdapter = new CustomAdapter(getActivity(), brandList);
                listView.setAdapter(customAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                        stringBrand = brandList.get(position);
                        view.dismiss();
                        brand.setText(stringBrand);
                        modelSalesList = Lists.newArrayList(Iterables.filter(salesList, new Predicate<Sales>() {
                            @Override
                            public boolean apply(Sales input) {
                                return (input.getBrand().equalsIgnoreCase(stringBrand));
                            }
                        }));

                        modelList = new ArrayList<String>();
                        for (int i = 0; i < modelSalesList.size(); i++) {
                            modelList.add(modelSalesList.get(i).getModelNo());
                        }
                        modelList.add("other");

                    }
                });

                view.show();
            }
        });


        accessoryType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> accessoriesList = new ArrayList();

                accessoriesList.add("Earphone");
                accessoriesList.add("cover");
                accessoriesList.add("screenguard");
                accessoriesList.add("battery");

                final Dialog view = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_NoActionBar);
                view.setContentView(R.layout.dialog_layout);

                TextView title = (TextView) view.findViewById(R.id.dialogtitle);
                title.setText("Accessory ");
                ListView listView = (ListView) view.findViewById(R.id.dialoglist);
                CustomAdapter customAdapter = new CustomAdapter(getActivity(), accessoriesList);
                listView.setAdapter(customAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                        stringAccessory = accessoriesList.get(position);
                        view.dismiss();
                        accessoryType.setText(stringAccessory);
                    }
                });

                view.show();
            }
        });

        modelNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringBrand == null) {
                    toast(getActivity(), "please select brand");
                } else {
//                    final List<String> modellist = new ArrayList();
//                    modellist.add("Galaxy y");
//                    modellist.add("galaxy s");
//                    modellist.add("galaxy s2");
//                    modellist.add("galaxy s3");
//                    modellist.add("other");

                    final Dialog view = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_NoActionBar);
                    view.setContentView(R.layout.dialog_layout);

                    TextView title = (TextView) view.findViewById(R.id.dialogtitle);
                    title.setText("Model no");
                    ListView listView = (ListView) view.findViewById(R.id.dialoglist);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), modelList);
                    listView.setAdapter(customAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                            stringModel = modelList.get(position);
                            view.dismiss();
                            if (stringModel.equalsIgnoreCase("other")) {
                                other.setVisibility(View.VISIBLE);
                            } else {
                                other.setVisibility(View.GONE);
                                other.getText().clear();
                            }


                            modelNo.setText(stringModel);
                        }
                    });

                    view.show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringProductType.equalsIgnoreCase(ProductType.ACCESSORY.name()) && stringAccessory == null) {
                    toast(getActivity(), "please select accessory type");

                } else if (stringBrand == null) {
                    toast(getActivity(), "please select brand");

                } else if (stringModel == null) {
                    toast(getActivity(), "please select model");

                } else if (quantity.getText().length() <= 0) {
                    toast(getActivity(), "please enter quantity");

                } else if (price.getText().length() <= 0) {
                    toast(getActivity(), "please enter price");

                } else {
                    if (stringModel.equalsIgnoreCase("other")) {
                        stringModel = other.getText().toString();
                    }


                    new SendData().execute();


                }
            }
        });


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    case R.id.radiomobile:
                        accessoryType.setVisibility(View.GONE);
                        stringProductType = ProductType.MOBILE.name();
                        accessoryType.setText("");
                        brand.setText("");
                        modelNo.setText("");
                        other.setText("");
                        quantity.getText().clear();
                        price.getText().clear();
                        other.setVisibility(View.GONE);
                        stringModel = null;
                        stringAccessory = null;
                        stringBrand = null;

                        break;
                    case R.id.radioAccessory:
                        accessoryType.setVisibility(View.VISIBLE);
                        stringProductType = ProductType.ACCESSORY.name();
                        accessoryType.setText("");
                        brand.setText("");
                        modelNo.setText("");
                        other.setText("");
                        quantity.getText().clear();
                        price.getText().clear();
                        other.setVisibility(View.GONE);
                        stringModel = null;
                        stringAccessory = null;
                        stringBrand = null;


                }
            }
        });


        return v;
    }


    private class SendData extends AsyncTask<Void, Void, Void> {

        MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new MaterialDialog.Builder(getActivity())
                    .content("plese wait")
                    .progress(true, 0)
                    .show();
        }


        @Override
        protected Void doInBackground(Void... params) {


            Sales sales = new Sales();
            sales.setProductType(stringProductType);
            sales.setBrand(stringBrand);
            sales.setModel(stringModel);
            sales.setQuantity(quantity.getText().toString());
            sales.setPrice(price.getText().toString());

            Client.INSTANCE.sales(sales, new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {

                    dialog.dismiss();


                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

            return null;
        }
    }
}
