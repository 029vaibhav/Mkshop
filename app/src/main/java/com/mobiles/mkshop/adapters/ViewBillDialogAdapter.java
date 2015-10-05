package com.mobiles.mkshop.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.ProductExpenseSingleEntry;

import java.awt.font.TextAttribute;
import java.util.List;

/**
 * Created by vaibhav on 6/10/15.
 */
public class ViewBillDialogAdapter extends RecyclerView.Adapter<ViewBillDialogAdapter.ViewHolder> {

    List<ProductExpenseSingleEntry> productExpenseSingleEntries;
    Fragment context;

    public ViewBillDialogAdapter(Fragment userListFragment, List<ProductExpenseSingleEntry> productExpenseSingleEntries)

    {
        context = userListFragment;
        this.productExpenseSingleEntries = productExpenseSingleEntries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bill_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProductExpenseSingleEntry productExpenseSingleEntry = productExpenseSingleEntries.get(position);
        holder.totalAmount.setText(productExpenseSingleEntry.getCreated());
        holder.dealerName.setText(context.getString(R.string.rs) + " " + productExpenseSingleEntry.getAmount());


    }

    @Override
    public int getItemCount() {

        if (productExpenseSingleEntries != null)
            return productExpenseSingleEntries.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView dealerName, totalAmount, dueAmount, date;

        public ViewHolder(View itemView) {
            super(itemView);


            dealerName = (TextView) itemView.findViewById(R.id.dealer_name);
            totalAmount = (TextView) itemView.findViewById(R.id.total_amt);
            dueAmount = (TextView) itemView.findViewById(R.id.due_amt);
            dueAmount.setVisibility(View.GONE);
            date = (TextView) itemView.findViewById(R.id.date);
            date.setVisibility(View.GONE);
        }
    }

}
