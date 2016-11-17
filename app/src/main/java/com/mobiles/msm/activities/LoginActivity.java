package com.mobiles.msm.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.models.Auth;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends Activity {

    SharedPreferences sharedPreferences;
    ProgressDialog materialDialog;

    @Override
    protected void onPause() {
        super.onPause();

        if ((materialDialog != null) && materialDialog.isShowing())
            materialDialog.dismiss();
        materialDialog = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("MKSHOP", Context.MODE_PRIVATE);
        materialDialog = new ProgressDialog(this);
        materialDialog.setMessage("please wait");
        materialDialog.setCancelable(false);


        if (sharedPreferences.getString("AUTH", null) != null) {
            MyApplication.AUTH = sharedPreferences.getString("AUTH", null);
            MyApplication.Username = sharedPreferences.getString("USERNAME", null);
            Intent intent = new Intent(LoginActivity.this, GetLoginDetailsActivity.class);
            startActivity(intent);
            finish();
        }


        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (username.getText().length() == 0)
                    MyApplication.toast(LoginActivity.this, "please enter username");
                else if (password.getText().length() == 0)
                    MyApplication.toast(LoginActivity.this, "please enter password");
                else {
                    if ((materialDialog != null) && !materialDialog.isShowing())
                        materialDialog.show();
                    Call<Auth> login = Client.INSTANCE.login(username.getText().toString(), password.getText().toString());
                    login.enqueue(new Callback<Auth>() {
                        @Override
                        public void onResponse(Call<Auth> call, retrofit2.Response<Auth> response) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MyApplication.AUTH = response.body().getAuth();
                            sharedPreferences.edit().putString("AUTH", MyApplication.AUTH).apply();
                            MyApplication.Username = username.getText().toString();
                            sharedPreferences.edit().putString("USERNAME", MyApplication.Username).apply();
                            Intent intent = new Intent(LoginActivity.this, GetLoginDetailsActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Auth> call, Throwable t) {

                            if (materialDialog != null && materialDialog.isShowing())
                                materialDialog.dismiss();
                            MyApplication.toast(LoginActivity.this, t.getMessage());

                        }
                    });


                }
            }
        });


    }


}
