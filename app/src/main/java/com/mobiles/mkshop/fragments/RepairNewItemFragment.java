package com.mobiles.mkshop.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.adapters.CustomAdapter;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.R;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepairNewItemFragment extends Fragment {

    public static String TAG = "RepairNewItemFragment";
    //  private RadioGroup radiogroup;
    private TextView brand, modelNo, date, status;
    EditText price, other, problem;
    Button submit;
    String Stringdate = "", stringModel, stringBrand, stringStatus;
    int index;
    RepairPojo service;
    DatePickerDialog datePickerDialog;
    android.support.v7.widget.AppCompatEditText jobNo;


    public static RepairNewItemFragment newInstance() {
        RepairNewItemFragment fragment = new RepairNewItemFragment();
        return fragment;
    }

    public RepairNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "RepairNewItemFragment";


        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_repair_new_item, container, false);


        brand = (TextView) v.findViewById(R.id.brandtext);
        status = (TextView) v.findViewById(R.id.status);
        date = (TextView) v.findViewById(R.id.datetext);
        modelNo = (TextView) v.findViewById(R.id.modeltext);
        price = (EditText) v.findViewById(R.id.priceEdit);
        other = (EditText) v.findViewById(R.id.otheredit);
        jobNo = (android.support.v7.widget.AppCompatEditText) v.findViewById(R.id.jobnoedit);
        problem = (EditText) v.findViewById(R.id.problemedit);
        submit = (Button) v.findViewById(R.id.submit);

        date.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Stringdate = "" + dayOfMonth + "-" + monthOfYear + "-" + year;
                        date.setText("" + dayOfMonth + "/" + monthOfYear);
                        datePickerDialog.dismiss();
                        Stringdate = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .items(R.array.items)
                        .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                if (text != null) {
                                    stringStatus = text.toString();
                                    if (!stringStatus.equalsIgnoreCase("Pending") || !stringStatus.equalsIgnoreCase("Delivered") || !stringStatus.equalsIgnoreCase("Returned")) {
                                        date.setVisibility(View.VISIBLE);
                                    } else {
                                        date.setVisibility(View.GONE);
                                    }

                                    if (stringStatus.equalsIgnoreCase("Returned")) {
                                        price.setVisibility(View.GONE);
                                        price.setText("0");
                                    } else {
                                        price.setVisibility(View.VISIBLE);
                                    }
                                    status.setText(stringStatus);
                                    setindex(stringStatus);
                                }
                                return true;
                            }
                        })
                        .positiveText("select")
                        .show();
            }
        });

        modelNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringBrand == null) {
                    MkShop.toast(getActivity(), "please select brand");
                } else {
                    final List<String> modellist = new ArrayList();
                    modellist.add("other");
                    modellist.add("gt");

                    final Dialog view = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_NoActionBar);
                    view.setContentView(R.layout.dialog_layout);

                    TextView title = (TextView) view.findViewById(R.id.dialogtitle);
                    title.setText("Model no");
                    ListView listView = (ListView) view.findViewById(R.id.dialoglist);
                    CustomAdapter customAdapter = new CustomAdapter(getActivity(), modellist);
                    listView.setAdapter(customAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view2, int position, long id) {
                            stringModel = modellist.get(position);
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
                if (stringBrand == null) {
                    MkShop.toast(getActivity(), "please select brand");

                } else if (stringModel == null) {
                    MkShop.toast(getActivity(), "please select model");

                } else if (!stringStatus.equalsIgnoreCase("Pending") && date.getText().length() <= 0 || date.getText().toString().equalsIgnoreCase("date")) {
                    MkShop.toast(getActivity(), "please select date");

                } else if (price.getText().length() <= 0) {
                    MkShop.toast(getActivity(), "please enter price");

                } else if (stringModel.equalsIgnoreCase("other") && other.getText().length() <= 0) {
                    MkShop.toast(getActivity(), "please enter model");

                } else {
                    if (stringModel.equalsIgnoreCase("other")) {
                        stringModel = other.getText().toString();
                    }

                    service = new RepairPojo();
                    service.setBrand(stringBrand);
                    service.setModelNo(stringModel);
                    service.setStatus(stringStatus);
                    service.setPrice("" + price.getText().toString());
                    service.setJobNo("" + jobNo.getText().toString());
                    service.setDeliveryDate(Stringdate);

                    if (MkShop.Role.equalsIgnoreCase("sm")) {
                        service.setPlace("shop");
                    } else if (MkShop.Role.equalsIgnoreCase("sc")) {
                        service.setPlace("service");
                    }
                    service.setProblem(problem.getText().toString());
                    service.setUsername("111111");

                    new SendData().execute();
                }
            }
        });


        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> brandList = new ArrayList();
                for (int i = 0; i < 20; i++) {
                    brandList.add("i");
                }

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
                    }
                });

                view.show();
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
                    .build();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Void... params) {


            Client.INSTANCE.sendService(service, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    MkShop.toast(getActivity(), s);
                    dialog.dismiss();
                    Fragment fragment = new RequestRepair();
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
                    @Override
                    public void failure (RetrofitError error){

                    }
                }

                );
                return null;
            }
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
}
