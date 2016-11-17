package com.mobiles.msm.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Message;

import org.joda.time.DateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationFragment extends Fragment {

    ProgressDialog dialog;
    public static String TAG = "SendNotificationFragment";
    String validityDate;
    String[] roleOption = {"Admin", "Salesman", "Technician", "Receptionist"};


    public static SendNotificationFragment newInstance() {
        SendNotificationFragment fragment = new SendNotificationFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    public SendNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_send_notification, container, false);

        dialog = NavigationMenuActivity.materialDialog;
        final EditText message = (EditText) viewGroup.findViewById(R.id.message);
        final TextView date = (TextView) viewGroup.findViewById(R.id.validity);
        final AutoCompleteTextView role = (AutoCompleteTextView) viewGroup.findViewById(R.id.role);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, roleOption);
        role.setThreshold(1);
        role.setAdapter(adapter);

        Button submit = (Button) viewGroup.findViewById(R.id.submit);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        String sToDate = MyApplication.checkDigit(i3) + "-" + MyApplication.checkDigit(i2 + 1) + "-" + i;
                        date.setText(sToDate);
                        DateTime dt = new DateTime(i, i2 + 1, i3, 01, 01);
                        validityDate = dt.toString(getString(R.string.date_format));

                    }
                }, DateTime.now().getYear(), DateTime.now().getMonthOfYear() - 1, DateTime.now().getDayOfMonth());
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (message.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please provide message");
                else if (role.getText().length() == 0)
                    MyApplication.toast(getActivity(), "please specify role");
                else if (date.getText().toString().equalsIgnoreCase("Validity"))
                    MyApplication.toast(getActivity(), "please select validity date");
                else {

                    dialog.show();

                    Message messageObject = new Message();
                    messageObject.setRole(role.getText().toString());
                    messageObject.setEndDate(validityDate);

                    messageObject.setMessage(message.getText().toString());
                    Client.INSTANCE.sendNotification(MyApplication.AUTH, messageObject).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                            MyApplication.toast(getActivity(), "Message sent successfully");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();

                                MyApplication.toast(getActivity(), t.getMessage());

                        }
                    });
                }


            }
        });


        return viewGroup;
    }


}
