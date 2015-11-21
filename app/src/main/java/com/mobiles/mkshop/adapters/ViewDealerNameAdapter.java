package com.mobiles.mkshop.adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.fragments.PaymentReportOfDealer;
import com.mobiles.mkshop.pojos.models.ProductExpense;

import java.util.List;

/**
 * Created by vaibhav on 3/10/15.
 */
public class ViewDealerNameAdapter extends RecyclerView.Adapter<ViewDealerNameAdapter.ViewHolder> {

    List<ProductExpense> productExpenseList;
    Fragment context;
    View view;


    public ViewDealerNameAdapter(Fragment viewDealersName, List<ProductExpense> paymentInfoHashMap) {
        this.context = viewDealersName;
        this.productExpenseList = paymentInfoHashMap;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bill_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewDealerNameAdapter.ViewHolder holder, int position) {


        ProductExpense productExpense = productExpenseList.get(position);


//        holder.date.setText(productExpense.getCreated());
        holder.dealerName.setText(productExpense.getDealerName());
        holder.totalAmount.setText("Total " + context.getString(R.string.rs) + " " + productExpense.getTotalAmt());

        if (productExpense.getDueAmount() < 0) {
            holder.dueAmount.setTextColor(Color.GREEN);
            holder.dueAmount.setText("Balance  " + context.getString(R.string.rs) + "  " + -productExpense.getDueAmount());
        } else {
            holder.dueAmount.setTextColor(Color.RED);
            holder.dueAmount.setText("Due " + context.getString(R.string.rs) + " " + productExpense.getDueAmount());
        }


    }

    @Override
    public int getItemCount() {
        return productExpenseList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dealerName, totalAmount, dueAmount, date;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            dealerName = (TextView) itemView.findViewById(R.id.dealer_name);
            totalAmount = (TextView) itemView.findViewById(R.id.total_amt);
            dueAmount = (TextView) itemView.findViewById(R.id.due_amt);
            date = (TextView) itemView.findViewById(R.id.date);
            cardView = (CardView) itemView.findViewById(R.id.cardlist_item);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


            PaymentReportOfDealer viewBills = PaymentReportOfDealer.newInstance(productExpenseList.get(getAdapterPosition()).getDealerName());
            context.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, viewBills,"DealerReportViewPagerFragment").commit();
        }
    }
}
