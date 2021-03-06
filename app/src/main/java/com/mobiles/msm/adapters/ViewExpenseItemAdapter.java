package com.mobiles.msm.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Myenum;
import com.mobiles.msm.pojos.enums.PaymentType;
import com.mobiles.msm.pojos.models.EmployeeExpense;

import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class ViewExpenseItemAdapter extends RecyclerView.Adapter<ViewExpenseItemAdapter.ViewHolder> {

    Fragment context;
    PaymentType paymentType;
    List<EmployeeExpense> salesList;
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


        EmployeeExpense sales = salesList.get(position);
        holder.paymentDate.setText(sales.getCreated().split("T")[0]);
        holder.name.setText(sales.getUsername());
        holder.revenue.setText(costsymbol + " " + sales.getAmount());
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
        public TextView name;
        public TextView revenue;
        public TextView paymentDate;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            name = (TextView) itemView.findViewById(R.id.name);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            paymentDate = (TextView) itemView.findViewById(R.id.date);


        }


    }
}
