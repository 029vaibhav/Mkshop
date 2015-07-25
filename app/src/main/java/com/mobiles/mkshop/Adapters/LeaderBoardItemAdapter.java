package com.mobiles.mkshop.Adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.Application.Myenum;
import com.mobiles.mkshop.Pojos.Leader;
import com.mobiles.mkshop.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vaibhav on 2/7/15.
 */
public class LeaderBoardItemAdapter extends RecyclerView.Adapter<LeaderBoardItemAdapter.ViewHolder> {

    Fragment context;
    String department;
    List<Leader> leaderList;

    public LeaderBoardItemAdapter(Fragment salesReportList, String department) {
        context = salesReportList;
        this.department = department;
        leaderList = Myenum.INSTANCE.getLeaderList(department);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Leader leader = leaderList.get(position);
        holder.name.setText(leader.getName());
        holder.qty.setText(leader.getQuantity());
        holder.revenue.setText(leader.getPrice());

    }


    @Override
    public int getItemCount() {
        if(leaderList!=null)return leaderList.size();
        else return 0;
    }




    public void sortquantiy(boolean sort) {

        leaderList = Myenum.INSTANCE.getLeaderList(department);
        Collections.sort(leaderList, new Comparator<Leader>() {
            @Override
            public int compare(Leader lhs, Leader rhs) {

                int a = Integer.parseInt(lhs.getQuantity());
                int b = Integer.parseInt(rhs.getQuantity());
                return a - b;
            }
        });

        if (sort) {
            leaderList = leaderList;

        } else {


            Collections.reverse(leaderList);

        }
        notifyDataSetChanged();

    }


    public void sortprice(boolean sort) {

        leaderList = Myenum.INSTANCE.getLeaderList(department);
        Collections.sort(leaderList, new Comparator<Leader>() {
            @Override
            public int compare(Leader lhs, Leader rhs) {

                int a = Integer.parseInt(lhs.getPrice());
                int b = Integer.parseInt(rhs.getPrice());
                return a - b;
            }
        });

        if (sort) {
            leaderList = leaderList;

        } else {


            Collections.reverse(leaderList);

        }
        notifyDataSetChanged();

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
