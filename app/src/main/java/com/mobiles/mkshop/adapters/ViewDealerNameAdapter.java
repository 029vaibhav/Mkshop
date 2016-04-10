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
import com.mobiles.mkshop.pojos.models.ExpenseManager;

import java.util.List;

/**
 * Created by vaibhav on 3/10/15.
 */
public class ViewDealerNameAdapter extends RecyclerView.Adapter<ViewDealerNameAdapter.ViewHolder> {

    List<ExpenseManager> expenseManagerList;
    Fragment context;
    View view;


    public ViewDealerNameAdapter(Fragment viewDealersName, List<ExpenseManager> paymentInfoHashMap) {
        this.context = viewDealersName;
        this.expenseManagerList = paymentInfoHashMap;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bill_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewDealerNameAdapter.ViewHolder holder, int position) {


        ExpenseManager expenseManager = expenseManagerList.get(position);
//        holder.date.setText(productExpense.getCreated());
        holder.dealerName.setText(expenseManager.getDealerInfo().getDealerName());
        holder.totalAmount.setText("Total " + context.getString(R.string.rs) + " " + expenseManager.getDealerInfo().getTotalAmount());

        int dueAmount = (int) (expenseManager.getDealerInfo().getTotalAmount() - expenseManager.getDealerInfo().getPayedAmount());
        if (dueAmount < 0) {
            holder.dueAmount.setTextColor(Color.GREEN);
            holder.dueAmount.setText("Balance  " + context.getString(R.string.rs) + "  " + -dueAmount);
        } else {
            holder.dueAmount.setTextColor(Color.RED);
            holder.dueAmount.setText("Due " + context.getString(R.string.rs) + " " + dueAmount);
        }


    }

    @Override
    public int getItemCount() {
        return expenseManagerList.size();
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


            Fragment viewBills = PaymentReportOfDealer.newInstance(expenseManagerList.get(getAdapterPosition()).getDealerInfo().getServerId());
            context.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, viewBills, "DealerReportViewPagerFragment").addToBackStack(null).commit();
        }
    }
}
