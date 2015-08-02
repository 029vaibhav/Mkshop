package com.mobiles.mkshop.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.LoginDetails;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetLoginDetailsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    MaterialDialog materialDialog;
    TextView textView;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_login_details);

        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);

        materialDialog = new MaterialDialog.Builder(GetLoginDetailsActivity.this)
                .progress(true, 0)
                .cancelable(false)
                .build();

        materialDialog.show();

        textView = (TextView) findViewById(R.id.internet);
        retry = (Button) findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetLoginDetailsActivity.this, GetLoginDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Client.INSTANCE.getLoginData(MkShop.AUTH, MkShop.Username, new Callback<LoginDetails>() {
            @Override
            public void success(LoginDetails loginDetails, Response response) {


                if(materialDialog !=null &&materialDialog.isShowing())
                materialDialog.dismiss();
                String json = new Gson().toJson(loginDetails);

                MkShop.Role = loginDetails.getRole();
                MkShop.Username = loginDetails.getUsername();

                sharedPreferences.edit().putString("DETAIL", json).apply();
                Intent intent = new Intent(GetLoginDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {

                if(materialDialog !=null &&materialDialog.isShowing())
                materialDialog.dismiss();
                if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
                    MkShop.toast(GetLoginDetailsActivity.this, "please check your internet connection");
                    textView.setText("Network Error");

                } else {
                    textView.setText(error.getMessage());
                }
                retry.setVisibility(View.VISIBLE);


            }
        });


    }


}
