package com.mobiles.mkshop.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.R;

import java.util.Collection;
import java.util.List;

/**
 * Created by vaibhav on 28/6/15.
 */
public class ServiceCenterAdapter extends BaseAdapter {

    Context context;
    List<RepairPojo> repairList;

    public ServiceCenterAdapter(Context context, List<RepairPojo> repairList) {
        this.context = context;
        this.repairList = repairList;

    }

    @Override
    public int getCount() {
        return repairList.size();
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
            convertView = inflater.inflate(R.layout.repair_list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.modelNo = (TextView) convertView.findViewById(R.id.repairmodel);
            viewHolder.date = (TextView) convertView.findViewById(R.id.repairdate);
            viewHolder.status = (TextView) convertView.findViewById(R.id.reapirstatus);
            viewHolder.jobNo = (TextView) convertView.findViewById(R.id.repairjobno);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        RepairPojo repairPojo = repairList.get(position);

        viewHolder.modelNo.setText(repairPojo.getBrand() + " " + repairPojo.getModel());
        viewHolder.date.setText(repairPojo.getCreated());
        viewHolder.status.setText(repairPojo.getStatus());
        viewHolder.jobNo.setText(repairPojo.getJobNo());


        return convertView;
    }

    public void filter(final Editable s) {


        if (s.length() > 0) {
            Collection<RepairPojo> repairPojoList = Collections2.filter(Myenum.INSTANCE.getServiceList(null),
                    new Predicate<RepairPojo>() {
                        @Override
                        public boolean apply(RepairPojo input) {
                            return (input.getBrand().contains(s) || input.getModel().contains(s) || input.getJobNo().contains(s)
                                    || input.getCreated().contains(s) || input.getStatus().toLowerCase().contains(s));
                        }
                    });
            repairList = Lists.newArrayList(repairPojoList);


        } else {

            repairList = Myenum.INSTANCE.getServiceList(null);

        }
        notifyDataSetInvalidated();

    }


    static class ViewHolderItem {

        TextView modelNo, date, status, jobNo;

    }

}
