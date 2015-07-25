package com.mobiles.mkshop.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mobiles.mkshop.Application.Client;
import com.mobiles.mkshop.Application.MkShop;
import com.mobiles.mkshop.Pojos.LoginDetails;
import com.mobiles.mkshop.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);


        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().length() == 0)
                    MkShop.toast(LoginActivity.this, "please enter username");
                else if (password.getText().length() == 0)
                    MkShop.toast(LoginActivity.this, "please enter password");
                else {

                    Client.INSTANCE.login(username.getText().toString(), password.getText().toString(), new Callback<LoginDetails>() {
                        @Override
                        public void success(LoginDetails response, Response response2) {

                            String json = new Gson().toJson(response);

                            MkShop.Role= response.getLocation().getRole();

                            sharedPreferences.edit().putString("DETAIL", json).apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {


                        }
                    });


                }
            }
        });


    }


}
