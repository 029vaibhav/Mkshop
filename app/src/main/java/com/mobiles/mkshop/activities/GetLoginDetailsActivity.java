package com.mobiles.mkshop.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.models.LoginDetails;
import com.mobiles.mkshop.pojos.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GetLoginDetailsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ProgressDialog materialDialog;
    TextView textView;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_login_details);

        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);

        materialDialog = new ProgressDialog(this);
        materialDialog.setMessage("please wait");
        materialDialog.setCancelable(false);
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

        Client.INSTANCE.getLoginData(MkShop.AUTH, MkShop.Username).enqueue(new Callback<LoginDetails>() {
            @Override
            public void onResponse(Call<LoginDetails> call, Response<LoginDetails> response) {

                LoginDetails loginDetails = response.body();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String jsonInString = mapper.writeValueAsString(loginDetails);
                    sharedPreferences.edit().putString("DETAIL", jsonInString).apply();
                } catch (JsonProcessingException e) {

                }
                MkShop.Role = loginDetails.getUser().getRole();
                MkShop.Username = loginDetails.getUser().getUsername();
                List<Product> productList = loginDetails.getProducts();
                for (Product product : productList) {
                    product.setBrand(product.getBrand());
                    product.setModel(product.getModel());
                    product.setSim(product.getSim());
                    product.setScreenSize(product.getScreenSize());
                    product.setDisplayType(product.getDisplayType());
                    product.setOs(product.getOs());
                    product.setiMemory(product.getiMemory());
                    product.seteMemory(product.geteMemory());
                    product.setfCamera(product.getfCamera());
                    product.setbCamera(product.getbCamera());
                    product.setWlan(product.getWlan());
                    product.setBluetooth(product.getBluetooth());
                    product.setNfc(product.getNfc());
                    product.setInfrared(product.getInfrared());
                    product.setRadio(product.getRadio());
                    product.setBattery(product.getBattery());
                    product.setPrice(product.getPrice());
                    product.setType(product.getType());
                    product.setAccessoryType(product.getAccessoryType());
                    long save = product.save();
                }
                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();
                Intent intent = new Intent(GetLoginDetailsActivity.this, NavigationMenuActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<LoginDetails> call, Throwable t) {

                if (materialDialog != null && materialDialog.isShowing())
                    materialDialog.dismiss();

                MkShop.toast(GetLoginDetailsActivity.this, t.getMessage());
                if (t.getMessage() == null) {
                    MkShop.toast(GetLoginDetailsActivity.this, "please check your internet connection");
                } else if (t.getMessage().contains("Unauthorized")) {
                    sharedPreferences.edit().putString("AUTH", null).apply();
                    sharedPreferences.edit().putString("USERNAME", null).apply();
                    sharedPreferences.edit().putString("DETAIL", null).apply();
                    Intent intent = new Intent(GetLoginDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                textView.setText(t.getMessage());
                retry.setVisibility(View.VISIBLE);


            }
        });


    }


}
