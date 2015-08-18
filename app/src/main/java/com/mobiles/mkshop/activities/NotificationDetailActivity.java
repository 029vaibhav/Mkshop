package com.mobiles.mkshop.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mobiles.mkshop.R;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText(getIntent().getStringExtra("message"));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
