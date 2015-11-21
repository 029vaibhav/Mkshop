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
import com.mobiles.mkshop.pojos.models.BrandModelList;
import com.mobiles.mkshop.pojos.models.LoginDetails;

import java.util.List;

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


                String json = new Gson().toJson(loginDetails);

                MkShop.Role = loginDetails.getRole();
                MkShop.Username = loginDetails.getUsername();

                List<BrandModelList> brandModelLists = loginDetails.getProductList();

                for (int i = 0; i < brandModelLists.size(); i++) {
                    List<BrandModelList> localParkingEvent = BrandModelList.find(BrandModelList.class, "server_id = ?", "" + brandModelLists.get(i).getId());
                    if (localParkingEvent == null || localParkingEvent.size() == 0) {
                        BrandModelList brandModelList = new BrandModelList();
                        brandModelList.setBrand(brandModelLists.get(i).getBrand().trim());
                        brandModelList.setModelNo(brandModelLists.get(i).getModelNo());
                        brandModelList.setType(brandModelLists.get(i).getType());
                        brandModelList.setAccessoryType(brandModelLists.get(i).getAccessoryType());
                        brandModelList.setServerId(brandModelLists.get(i).getId());
                        brandModelList.save();
                    }
                    else if(!localParkingEvent.get(0).equals(brandModelLists.get(i)))
                    {
                        BrandModelList brandModelList = localParkingEvent.get(0);
                        brandModelList.setBrand(brandModelLists.get(i).getBrand().trim());
                        brandModelList.setModelNo(brandModelLists.get(i).getModelNo());
                        brandModelList.setType(brandModelLists.get(i).getType());
                        brandModelList.setAccessoryType(brandModelLists.get(i).getAccessoryType());
                        brandModelList.setServerId(brandModelLists.get(i).getId());
                        brandModelList.save();
                    }

                }
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                sharedPreferences.edit().putString("DETAIL", json).apply();
                Intent intent = new Intent(GetLoginDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {

                if (materialDialog != null && materialDialog.isShowing())
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
