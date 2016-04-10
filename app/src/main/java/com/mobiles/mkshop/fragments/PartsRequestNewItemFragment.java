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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.enums.Status;
import com.mobiles.mkshop.pojos.models.PartsRequests;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PartsRequestNewItemFragment extends Fragment {


    public static String TAG = "RepairNewItemFragment";
    private TextView date, status, dateTitle;
    EditText price, customerName, part, mobileNo;
    Button submit;
    String Stringdate, stringStatus;
    int index;
    ProgressDialog dialog;


    public static PartsRequestNewItemFragment newInstance() {
        PartsRequestNewItemFragment fragment = new PartsRequestNewItemFragment();
        return fragment;
    }

    public PartsRequestNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MkShop.SCRREN = "PartsRequestNewItemFragment";
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.request_part_new_item, container, false);
        customerName = (EditText) v.findViewById(R.id.customername);
        status = (TextView) v.findViewById(R.id.status);
        dateTitle = (TextView) v.findViewById(R.id.date_title);
        date = (TextView) v.findViewById(R.id.datetext);
        price = (EditText) v.findViewById(R.id.priceEdit);
        mobileNo = (EditText) v.findViewById(R.id.mobile);
        part = (EditText) v.findViewById(R.id.partsrequired);
        submit = (Button) v.findViewById(R.id.submit);
        dialog = NavigationMenuActivity.materialDialog;

        date.setOnClickListener(new View.OnClickListener() {

            DatePickerDialog datePickerDialog;

            @Override
            public void onClick(View v) {

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Stringdate = "" + year + "-" + monthOfYear + "-" + dayOfMonth;
                        date.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year);
                        datePickerDialog.dismiss();

                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth());
                datePickerDialog.show();
            }
        });


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> statusOfParts = Arrays.asList(getResources().getStringArray(R.array.requestPartStatus));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final ArrayAdapter<String> aa1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, statusOfParts);
                builder.setSingleChoiceItems(aa1, index, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        String text = statusOfParts.get(item);
                        if (text != null) {
                            stringStatus = text.toString();
                            if (!stringStatus.equalsIgnoreCase("Pending")) {
                                date.setVisibility(View.VISIBLE);
                                dateTitle.setVisibility(View.VISIBLE);
                            } else {
                                date.setVisibility(View.GONE);
                                dateTitle.setVisibility(View.GONE);
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


        submit.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          if (customerName.getText().toString().length() <= 0) {
                                              MkShop.toast(getActivity(), "please enter customer name");

                                          } else if (mobileNo.getText().length() < 10 || mobileNo.getText().length() > 10) {
                                              MkShop.toast(getActivity(), "check mobile no");

                                          } else if (!stringStatus.equalsIgnoreCase(Status.PENDING.name()) && date.getText().length() == 0) {
                                              MkShop.toast(getActivity(), "please select delivery date");

                                          } else if (price.getText().length() <= 0) {
                                              MkShop.toast(getActivity(), "please enter price");

                                          } else if (part.getText().length() <= 0) {
                                              MkShop.toast(getActivity(), "please enter part required");

                                          } else {


                                              PartsRequests partsRequests = new PartsRequests();
                                              partsRequests.setCustomerName(customerName.getText().toString());
                                              partsRequests.setMobileNo(mobileNo.getText().toString());
                                              partsRequests.setStatus(stringStatus);
                                              partsRequests.setPrice(price.getText().toString());
                                              partsRequests.setPart(part.getText().toString());

                                              if (date.getText().toString().length() > 0 && !date.getText().toString().equalsIgnoreCase("date"))
                                                  partsRequests.setDeliveryDate(Stringdate);

                                              dialog.show();
                                              Client.INSTANCE.sendPartRequest(MkShop.AUTH, partsRequests).enqueue(new Callback<String>() {
                                                  @Override
                                                  public void onResponse(Call<String> call, Response<String> response) {
                                                      if (dialog != null && dialog.isShowing())
                                                          dialog.dismiss();
                                                      Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                                      Fragment fragment = new PartsRequestFragment();
                                                      getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                                                  }

                                                  @Override
                                                  public void onFailure(Call<String> call, Throwable t) {
                                                      if (dialog != null && dialog.isShowing())
                                                          dialog.dismiss();

                                                          MkShop.toast(getActivity(), t.getMessage());
                                                  }
                                              });


                                          }
                                      }
                                  }

        );


        return v;
    }


    private int setindex(String status) {


        if (status.equalsIgnoreCase("Pending")) {
            index = 0;
        } else if (status.equalsIgnoreCase("Recieved")) {
            index = 1;
        } else if (status.equalsIgnoreCase("Delivered")) {
            index = 2;
        }
        return index;

    }
}
