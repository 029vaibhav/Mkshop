package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.pojos.Status;
import com.mobiles.mkshop.R;

import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class ServiceReportItemAdapter extends RecyclerView.Adapter<ServiceReportItemAdapter.ViewHolder> {

    Fragment context;
    Status status;
    List<RepairPojo> serviceList;

    public ServiceReportItemAdapter(Fragment salesReportList, Status status) {
        context = salesReportList;
        this.status = status;
        serviceList = Myenum.INSTANCE.getServiceList(status);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_report_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RepairPojo repairPojo = serviceList.get(position);
        holder.brand.setText(repairPojo.getModel());
        holder.model.setText(repairPojo.getBrand());
        holder.place.setText(repairPojo.getPlace());
        holder.revenue.setText(context.getActivity().getString(R.string.rs) + " " + repairPojo.getPrice());
        holder.revenue.setTextColor(ContextCompat.getColor(context.getActivity(), R.color.flatGreen));
        holder.problem.setText(repairPojo.getProblem());
        //   holder.date.setText(repairPojo.getModifiedDate());

    }


    @Override
    public int getItemCount() {
        if (serviceList != null)
            return serviceList.size();
        else return 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView brand;
        public TextView model;
        public TextView place;
        public TextView revenue;
        public TextView problem;
        //   public TextView date;


        public ViewHolder(View row) {
            super(row);
            brand = (TextView) row.findViewById(R.id.Brand);
            model = (TextView) row.findViewById(R.id.model);
            place = (TextView) row.findViewById(R.id.place);
            revenue = (TextView) row.findViewById(R.id.revenue);
            problem = (TextView) row.findViewById(R.id.problem);
            //   date = (TextView) row.findViewById(R.id.date);

        }
    }
}
