package com.mobiles.mkshop.fragments;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.RepairPojo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class RepairListItemFragment extends Fragment {


    public static String TAG = "serviceItem";
    private TextView brand, modelNo, date, status;
    EditText price, other, jobNo, problem;
    Button submit;
    String stringStatus;
    int index;
    int id;
    RepairPojo service;
    DateTime dateTime;


    public static RepairListItemFragment newInstance() {
        RepairListItemFragment fragment = new RepairListItemFragment();
        return fragment;
    }

    public RepairListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "RepairListItemFragment";

        service = Myenum.INSTANCE.getRepairPojo();

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_repair_list_item, container, false);
        brand = (TextView) v.findViewById(R.id.brandtext);
        date = (TextView) v.findViewById(R.id.datetext);
        status = (TextView) v.findViewById(R.id.status);
        modelNo = (TextView) v.findViewById(R.id.modeltext);
        price = (EditText) v.findViewById(R.id.priceEdit);
        other = (EditText) v.findViewById(R.id.otheredit);
        jobNo = (EditText) v.findViewById(R.id.jobnoedit);
        problem = (EditText) v.findViewById(R.id.problemedit);
        submit = (Button) v.findViewById(R.id.submit);

        problem.setText(service.getProblem());
        brand.setText(service.getBrand());
        modelNo.setText(service.getModel());
        final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        dateTime = formatter.parseDateTime(service.getDeliveryDate());
        date.setText(dateTime.toString("dd-MM"));
        price.setText("" + service.getPrice());
        jobNo.setText(service.getJobNo());

        jobNo.setEnabled(false);
        status.setText(service.getStatus());
        stringStatus = service.getStatus();
        setindex(service.getStatus());


        date.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog datePickerDialog;

            @Override
            public void onClick(View v) {

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String Stringdate = "" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dateTime = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
                        date.setText("" + dayOfMonth + "-" + monthOfYear);
                        datePickerDialog.dismiss();

                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() + 1, DateTime.now().getDayOfMonth());
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
                                stringStatus = text.toString();
                                if (stringStatus.equalsIgnoreCase("Pending") || stringStatus.equalsIgnoreCase("Delivered") || stringStatus.equalsIgnoreCase("Returned")) {

                                    date.setVisibility(View.GONE);
                                } else {
                                    date.setVisibility(View.VISIBLE);
                                }


                                if (stringStatus.equalsIgnoreCase("Returned")) {
                                    price.setVisibility(View.GONE);
                                    price.setText("0");
                                } else {
                                    price.setVisibility(View.VISIBLE);
                                }
                                status.setText(stringStatus);
                                setindex(stringStatus);
                                return true;
                            }
                        })
                        .positiveText("select")
                        .show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                service.setPrice(price.getText().toString());
                service.setStatus(stringStatus);
                service.setProblem(problem.getText().toString());
                service.setDeliveryDate(dateTime.toString("yyyy-MM-dd"));
                service.setUsername(MkShop.Username);
                new SendData().execute();
            }
        });


        return v;
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

    private class SendData extends AsyncTask<Void, Void, Void> {

        MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new MaterialDialog.Builder(getActivity())
                    .content("please wait")
                    .progress(true, 0)
                    .build();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {


            Client.INSTANCE.sendService(MkShop.AUTH, service, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    MkShop.toast(getActivity(), s);
                    Fragment fragment = new RequestRepair();

                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
            return null;
        }
    }

}
