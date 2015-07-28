package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.ProductType;
import com.mobiles.mkshop.pojos.Sales;
import com.mobiles.mkshop.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class SalesReportItemAdapter extends RecyclerView.Adapter<SalesReportItemAdapter.ViewHolder> {

    Fragment context;
    ProductType productType;
    List<Sales> salesList;

    public SalesReportItemAdapter(Fragment salesReportList, ProductType productType) {
        context = salesReportList;
        this.productType = productType;
        salesList = Myenum.INSTANCE.getSalesList(productType);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Sales sales = salesList.get(position);
        holder.brand.setText(sales.getModelNo());
        holder.model.setText(sales.getBrand());
        holder.quantity.setText(sales.getQuantity());
        holder.revenue.setText(sales.getPrice());

        if (productType == ProductType.Accessory) {
            holder.accessoryType.setVisibility(View.VISIBLE);
            holder.accessoryType.setText(sales.getAccessoryType());
        }

    }


    @Override
    public int getItemCount() {
        if (salesList == null) {
            return 0;
        }
        return salesList.size();
    }



    public void sortquantiy(boolean sort) {

        salesList = Myenum.INSTANCE.getSalesList(productType);
        Collections.sort(salesList, new Comparator<Sales>() {
            @Override
            public int compare(Sales lhs, Sales rhs) {

                int a = Integer.parseInt(lhs.getQuantity());
                int b = Integer.parseInt(rhs.getQuantity());
                return a - b;
            }
        });

        if (sort) {
            salesList = salesList;

        } else {


            Collections.reverse(salesList);

        }
        notifyDataSetChanged();

    }


    public void sortprice(boolean sort) {

        salesList = Myenum.INSTANCE.getSalesList(productType);
        Collections.sort(salesList, new Comparator<Sales>() {
            @Override
            public int compare(Sales lhs, Sales rhs) {

                int a = Integer.parseInt(lhs.getPrice());
                int b = Integer.parseInt(rhs.getPrice());
                return a - b;
            }
        });

        if (sort) {
            salesList = salesList;

        } else {


            Collections.reverse(salesList);

        }
        notifyDataSetChanged();

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardItemLayout;
        public TextView brand;
        public TextView model;
        public TextView quantity;
        public TextView revenue;
        public TextView accessoryType;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            brand = (TextView) itemView.findViewById(R.id.Brand);
            model = (TextView) itemView.findViewById(R.id.model);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            accessoryType = (TextView) itemView.findViewById(R.id.accessoryType);


        }


    }
}
