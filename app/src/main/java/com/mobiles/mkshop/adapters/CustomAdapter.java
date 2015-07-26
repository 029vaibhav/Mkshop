package com.mobiles.mkshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobiles.mkshop.R;

import java.util.List;

/**
 * Created by vaibhav on 27/6/15.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<String>  stringList;

    public CustomAdapter(Context activity, List<String> stringList) {

        context=activity;
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.dialog_list_item,null);
        TextView textView = (TextView)convertView.findViewById(R.id.itemtext);
        textView.setText(stringList.get(position));
        return convertView;
    }
}
