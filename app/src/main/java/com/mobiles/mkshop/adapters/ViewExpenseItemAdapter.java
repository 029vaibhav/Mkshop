package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.ExpenseEntity;
import com.mobiles.mkshop.pojos.PaymentType;

import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class ViewExpenseItemAdapter extends RecyclerView.Adapter<ViewExpenseItemAdapter.ViewHolder> {

    Fragment context;
    PaymentType paymentType;
    List<ExpenseEntity> salesList;
    String costsymbol;

    public ViewExpenseItemAdapter(Fragment salesReportList, PaymentType paymentType) {
        context = salesReportList;
        this.paymentType = paymentType;
        salesList = Myenum.INSTANCE.getExpenseList(paymentType);
        costsymbol = context.getResources().getString(R.string.Rs);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_report_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        ExpenseEntity sales = salesList.get(position);

        holder.paymentDate.setText(sales.getPaymentDate());
        if (paymentType == PaymentType.Product) {


            holder.brand.setText(sales.getModelNo());
            holder.model.setText(sales.getBrand());
            holder.quantity.setText(sales.getQuantity());
            holder.revenue.setText(costsymbol + " " + sales.getAmount());
            if (sales.getAccessoryType() != null && sales.getAccessoryType() != "") {
                holder.accessoryType.setVisibility(View.VISIBLE);
                holder.accessoryType.setText(sales.getAccessoryType());
            }
        } else {
            holder.brand.setText(sales.getName());
            holder.model.setVisibility(View.GONE);
            holder.quantity.setText("");
            holder.revenue.setText(costsymbol + " " + sales.getAmount());
        }


    }


    @Override
    public int getItemCount() {
        if (salesList == null) {
            return 0;
        }
        return salesList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardItemLayout;
        public TextView brand;
        public TextView model;
        public TextView quantity;
        public TextView revenue;
        public TextView accessoryType;
        public TextView paymentDate;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            brand = (TextView) itemView.findViewById(R.id.Brand);
            model = (TextView) itemView.findViewById(R.id.model);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            accessoryType = (TextView) itemView.findViewById(R.id.accessoryType);
            paymentDate = (TextView) itemView.findViewById(R.id.date);


        }


    }
}
