package com.mobiles.msm.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.msm.R;
import com.mobiles.msm.fragments.ViewProductItemFragment;
import com.mobiles.msm.pojos.models.Product;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 2/7/15.
 */
public class ViewProductadapter extends RecyclerView.Adapter<ViewProductadapter.ViewHolder> {

    Context context;

    List<Product> salesList;
    Fragment fragmentx;
    private List<Product> filteredsalesListList;

    public ViewProductadapter(Fragment fragment, Context salesReportList, List<Product> modelListByBrand) {
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

        final Product sales = salesList.get(position);

//        if (sales.getPath() != null && sales.getPath().size()>0) {
//            String url = sales.getPath().get(0).replace("\\", "");
//            Picasso.with(context).load(url).into(holder.moibleImageView);
//        } else
//            Picasso.with(context).load("https://www.itelligence.com/Content/Images/no-image.png").into(holder.moibleImageView);

        holder.model.setText(sales.getModel());


    }


    @Override
    public int getItemCount() {
        if (salesList == null) {
            return 0;
        }
        return salesList.size();
    }

    public void filter(final Editable s) {

        if (s.length() > 0) {
            Collection<Product> salesCollection = Collections2.filter(filteredsalesListList,
                    new Predicate<Product>() {
                        @Override
                        public boolean apply(Product input) {
                            return (input.getModel().toLowerCase(Locale.ENGLISH).contains(s.toString()));
                        }
                    });

            salesList = Lists.newArrayList(salesCollection);


        } else {

            salesList = filteredsalesListList;

        }
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardItemLayout;
        public ImageView moibleImageView;
        public TextView model;


        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            moibleImageView = (ImageView) itemView.findViewById(R.id.mobileImage);
            model = (TextView) itemView.findViewById(R.id.model);

            cardItemLayout.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            final Product sales = salesList.get(getAdapterPosition());
            ViewProductItemFragment fragment = ViewProductItemFragment.newInstance(sales.getId());
            fragmentx.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }
    }
}


