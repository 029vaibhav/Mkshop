package com.mobiles.msm.adapters;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.pojos.models.Sales;

import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class IncentiveUserlistDialogAdapter extends RecyclerView.Adapter<IncentiveUserlistDialogAdapter.ViewHolder> {

    Fragment context;
    List<Sales> userlist;
    String costSymbol;


    public IncentiveUserlistDialogAdapter(Fragment salesReportList, List<Sales> userlist) {
        context = salesReportList;
        this.userlist = userlist;


        costSymbol = context.getResources().getString(R.string.Rs);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_incentive_user_sale_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Sales leader = userlist.get(position);
        holder.date.setText(leader.getCreated());
        holder.price.setText(costSymbol+" " +leader.getPrice());
//        holder.revenue.setText(leader.getPrice());

    }


    @Override
    public int getItemCount() {
        if (userlist != null)
            return userlist.size();
        else return 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView price;


        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.date);
            price = (TextView) itemView.findViewById(R.id.price);

        }
    }
}
