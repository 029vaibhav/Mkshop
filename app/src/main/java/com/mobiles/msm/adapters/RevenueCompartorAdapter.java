package com.mobiles.msm.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
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
public class RevenueCompartorAdapter extends RecyclerView.Adapter<RevenueCompartorAdapter.ViewHolder> {

    Context context;
    List<Sales> priceCompartorServices;


    public RevenueCompartorAdapter(Activity activity, List<Sales> response1) {
        context = activity;
        priceCompartorServices = response1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Sales priceCompartorService = priceCompartorServices.get(position);
        if (priceCompartorService.getProductType() != null)
            holder.brand.setText(priceCompartorService.getProductType().name());
        else
            holder.brand.setText(priceCompartorService.getBrand());
//        holder.brand.setCompoundDrawablesWithIntrinsicBounds(MkShop.GetImage(priceCompartorService.getBrand()), 0, 0, 0);

        holder.quantity.setText(priceCompartorService.getQuantity());
        if (priceCompartorService.getAccessoryType() != null)
            holder.model.setText(priceCompartorService.getAccessoryType());
        else
            holder.model.setVisibility(View.GONE);
        if (priceCompartorService.getPrice() != null)
            holder.revenue.setText(context.getString(R.string.rs) + " " + priceCompartorService.getPrice());
        else
            holder.revenue.setText(context.getString(R.string.rs) + " " + "0");


    }


    @Override
    public int getItemCount() {
        if (priceCompartorServices == null)
            return 0;
        else {

            return priceCompartorServices.size();

        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardItemLayout;
        public TextView brand;
        public TextView model;
        public TextView quantity;
        public TextView revenue;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            brand = (TextView) itemView.findViewById(R.id.Brand);
            model = (TextView) itemView.findViewById(R.id.model);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            revenue = (TextView) itemView.findViewById(R.id.revenue);


        }


    }
}
