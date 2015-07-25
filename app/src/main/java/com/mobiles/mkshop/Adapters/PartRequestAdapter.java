package com.mobiles.mkshop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobiles.mkshop.Pojos.PartsRequests;
import com.mobiles.mkshop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 29/6/15.
 */
public class PartRequestAdapter extends BaseAdapter {

    Context context;
    List<PartsRequests> repairList;
    ArrayList<PartsRequests> partsRequestsArrayList;

    public PartRequestAdapter(Context context, List<PartsRequests> repairList) {
        this.context = context;
        this.repairList = repairList;
        partsRequestsArrayList = new ArrayList<PartsRequests>();
        partsRequestsArrayList.addAll(repairList);

    }

    @Override
    public int getCount() {

        if (repairList != null)
            return repairList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return repairList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;
        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.part_request_list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.customerName = (TextView) convertView.findViewById(R.id.customerName);
            viewHolder.partName = (TextView) convertView.findViewById(R.id.partName);
            viewHolder.status = (TextView) convertView.findViewById(R.id.reapirstatus);
            viewHolder.date = (TextView) convertView.findViewById(R.id.repairjobno);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        PartsRequests requestRepair = repairList.get(position);

        viewHolder.customerName.setText(requestRepair.getCustomerName());
        viewHolder.date.setText(requestRepair.getCreatedDate());
        viewHolder.status.setText(requestRepair.getStatus());
        viewHolder.partName.setText(requestRepair.getPart());


        return convertView;
    }


    static class ViewHolderItem {

        TextView customerName, partName, status, date;

    }

    public void Filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        repairList.clear();
        if (charText.length() == 0) {
            repairList.addAll(partsRequestsArrayList);
        } else {
            for (PartsRequests wp : partsRequestsArrayList) {
                if (wp.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getCreatedDate().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getPart().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getStatus().toLowerCase(Locale.getDefault()).contains(charText)
                        ) {
                    repairList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
