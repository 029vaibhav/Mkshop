package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.pojos.Sales;

import java.util.List;

/**
 * Created by vaibhav on 9/10/15.
 */
public class LeaderBoardDialogAdapter extends RecyclerView.Adapter<LeaderBoardDialogAdapter.ViewHolder> {

    Fragment context;
    List<Sales> sales;

    public LeaderBoardDialogAdapter(Fragment context, List<Sales> sales) {

        this.context = context;
        this.sales = sales;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_dialog, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Sales sale = sales.get(position);

        holder.brand.setText(sale.getModel());
        holder.model.setText(sale.getBrand());
        holder.date.setText(sale.getCreated().substring(0, 10));
        holder.revenue.setText(context.getString(R.string.rs) + "" + sale.getPrice());
        if (sale.getAccessoryType() != null && sale.getAccessoryType().length() > 0)
            holder.accessoryType.setText(sale.getAccessoryType());
        else
            holder.accessoryType.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView brand;
        public TextView model;
        public TextView accessoryType;
        public TextView revenue;
        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);


            brand = (TextView) itemView.findViewById(R.id.Brand);
            model = (TextView) itemView.findViewById(R.id.model);
            accessoryType = (TextView) itemView.findViewById(R.id.accessoryType);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            date = (TextView) itemView.findViewById(R.id.date);


        }


    }
}
