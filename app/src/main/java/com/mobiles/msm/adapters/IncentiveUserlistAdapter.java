package com.mobiles.msm.adapters;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.ListMultimap;
import com.mobiles.msm.R;
import com.mobiles.msm.fragments.IncentiveSalesUserListFragment;
import com.mobiles.msm.pojos.models.Sales;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class IncentiveUserlistAdapter extends RecyclerView.Adapter<IncentiveUserlistAdapter.ViewHolder> {

    Fragment context;
    ListMultimap<String, Sales> userlist;
    List<String> list;
    String id, message;

    public IncentiveUserlistAdapter(Fragment salesReportList, ListMultimap<String, Sales> userlist, String id, String message) {
        context = salesReportList;
        this.userlist = userlist;
        list = new ArrayList<>();

        list.addAll(userlist.asMap().keySet());
        this.id = id;
        this.message = message;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Sales leader = userlist.get
        holder.name.setText(list.get(position));
        holder.qty.setVisibility(View.GONE);
        holder.revenue.setVisibility(View.GONE);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                IncentiveSalesUserListFragment incentiveSalesUserListFragment =
                        IncentiveSalesUserListFragment.newInstance(userlist.get(list.get(position)), id, message);
                context.getFragmentManager().beginTransaction().replace(R.id.container, incentiveSalesUserListFragment).addToBackStack(null).commit();


            }
        });
    }


    @Override
    public int getItemCount() {
        if (userlist != null) return userlist.asMap().size();
        else return 0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView revenue;
        public TextView qty;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            qty = (TextView) itemView.findViewById(R.id.quantity);
        }
    }
}
