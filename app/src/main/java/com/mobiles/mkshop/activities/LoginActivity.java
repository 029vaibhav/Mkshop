package com.mobiles.mkshop.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    SharedPreferences sharedPreferences;
    MaterialDialog materialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                .progress(true, 0)
                .cancelable(false)
                .build();


        if (sharedPreferences.getString("AUTH", null) != null) {
            MkShop.AUTH = sharedPreferences.getString("AUTH", null);
            MkShop.Username = sharedPreferences.getString("USERNAME", null);
            Intent intent = new Intent(LoginActivity.this, GetLoginDetailsActivity.class);
            startActivity(intent);
            finish();
        }


        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().length() == 0)
                    MkShop.toast(LoginActivity.this, "please enter username");
                else if (password.getText().length() == 0)
                    MkShop.toast(LoginActivity.this, "please enter password");
                else {
                    materialDialog.show();
                    Client.INSTANCE.login(username.getText().toString(), password.getText().toString(), new Callback<String>() {
                        @Override
                        public void success(String response, Response response2) {


                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MkShop.AUTH = response;
                            sharedPreferences.edit().putString("AUTH", MkShop.AUTH).apply();
                            MkShop.Username = username.getText().toString();
                            sharedPreferences.edit().putString("USERNAME", MkShop.Username).apply();
                            Intent intent = new Intent(LoginActivity.this, GetLoginDetailsActivity.class);
                            startActivity(intent);
                            finish();


                        }

                        @Override
                        public void failure(RetrofitError error) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                MkShop.toast(LoginActivity.this, "please check your internet connection");

                            else
                                MkShop.toast(LoginActivity.this, error.getMessage());

                        }
                    });


                }
            }
        });


    }


}
