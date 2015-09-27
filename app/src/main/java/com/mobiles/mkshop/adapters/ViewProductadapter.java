package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.fragments.ViewProductItemFragment;
import com.mobiles.mkshop.pojos.RepairPojo;
import com.mobiles.mkshop.pojos.Sales;
import com.mobiles.mkshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class ViewProductadapter extends RecyclerView.Adapter<ViewProductadapter.ViewHolder> {

    Context context;

    List<Sales> salesList;
    Fragment fragmentx;
    private List<Sales> filteredsalesListList;

    public ViewProductadapter(Fragment fragment, Context salesReportList, List<Sales> modelListByBrand) {
        context = salesReportList;
        salesList = modelListByBrand;
        this.fragmentx = fragment;
        filteredsalesListList = salesList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewproduct_gridview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Sales sales = salesList.get(position);

        if (sales.getPath() != null) {
            String url = sales.getPath().replace("\\", "");
            Picasso.with(context).load(url).into(holder.moibleImageView);
        } else
            Picasso.with(context).load("https://www.itelligence.com/Content/Images/no-image.png").into(holder.moibleImageView);

        holder.model.setText(sales.getModelNo());

        holder.moibleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProductItemFragment fragment = ViewProductItemFragment.newInstance("" + sales.getId());
//
                fragmentx.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


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
        public ImageView moibleImageView;
        public TextView model;


        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            moibleImageView = (ImageView) itemView.findViewById(R.id.mobileImage);
            model = (TextView) itemView.findViewById(R.id.model);


        }


    }

    public void filter(final Editable s) {


        if (s.length() > 0) {
            Collection<Sales> salesCollection = Collections2.filter(filteredsalesListList,
                    new Predicate<Sales>() {
                        @Override
                        public boolean apply(Sales input) {
                            return (input.getModelNo().toLowerCase().contains(s.toString()));
                        }
                    });

            salesList = Lists.newArrayList(salesCollection);


        } else {

            salesList = filteredsalesListList;

        }
        notifyDataSetChanged();

    }


    private class FilterList extends Filter {

        private ViewProductadapter adapter;
        private final List<Sales> originalLists;
        private List<Sales> filteredList;


        private FilterList(ViewProductadapter adapter, List<Sales> salesList) {
            super();
            this.adapter = adapter;
            this.originalLists = salesList;
            this.filteredList = new ArrayList<>();

        }

        @Override
        protected FilterResults performFiltering(final CharSequence s) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (s.length() > 0) {
                Collection<Sales> salesCollection = Collections2.filter(salesList,
                        new Predicate<Sales>() {
                            @Override
                            public boolean apply(Sales input) {
                                return (input.getModelNo().toLowerCase().matches(s.toString()));
                            }
                        });

                filteredList = Lists.newArrayList(salesCollection);


            } else {

                filteredList.addAll(originalLists);

            }
            Log.e("size", "" + filteredList);
            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredsalesListList.clear();
            adapter.filteredsalesListList.addAll((ArrayList<Sales>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}


