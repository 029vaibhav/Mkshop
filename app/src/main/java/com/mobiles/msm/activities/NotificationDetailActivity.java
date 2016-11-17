package com.mobiles.msm.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.msm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);


        List<HashMap> mapList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        JSONObject jObject = null;
        String message = getIntent().getStringExtra("message");
        try {
            jObject = new JSONObject(message);
            Iterator<?> keys = jObject.keys();
            while (keys.hasNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                String key = (String) keys.next();
                String value = jObject.getString(key);
                if (value != null && value.length() != 0 && !value.equalsIgnoreCase("null")) {
                    map.put(key, value);
                    mapList.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("message", message);
            mapList.add(map);
        }


        NotificationAdapter notificationAdapter = new NotificationAdapter(mapList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notificationAdapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

        List<HashMap> hashMaps;

        public NotificationAdapter(List<HashMap> hashMaps) {

            this.hashMaps = hashMaps;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return hashMaps.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            HashMap<String, String> hashMap = hashMaps.get(position);
            Map.Entry<String, String> next = hashMap.entrySet().iterator().next();
            holder.title.setText(next.getKey());
            holder.subTitle.setText(next.getValue());
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            EditText subTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.textview_title);
                subTitle = (EditText) itemView.findViewById(R.id.detail);
            }
        }

    }
}
