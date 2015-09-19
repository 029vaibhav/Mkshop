package com.mobiles.mkshop.adapters;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.Leader;
import com.mobiles.mkshop.pojos.Sales;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vaibhav on 2/7/15.
 */
public class LeaderBoardItemAdapter extends RecyclerView.Adapter<LeaderBoardItemAdapter.ViewHolder> {

    Fragment context;
    String department;
    List<Leader> leaderList;
    MaterialDialog materialDialog;

    public LeaderBoardItemAdapter(Fragment salesReportList, String department) {
        context = salesReportList;
        this.department = department;
        leaderList = Myenum.INSTANCE.getLeaderList(department);


        materialDialog = new MaterialDialog.Builder(context.getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();


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
        holder.name.setText(leader.getName().toUpperCase());
        holder.qty.setText(leader.getQuantity());
        holder.revenue.setText(leader.getPrice());

    }


    @Override
    public int getItemCount() {
        if (leaderList != null) return leaderList.size();
        else return 0;
    }


    public void sortquantiy(boolean sort) {

        leaderList = Myenum.INSTANCE.getLeaderList(department);

        if (leaderList != null) {
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
    }


    public void sortprice(boolean sort) {

        leaderList = Myenum.INSTANCE.getLeaderList(department);

        if (leaderList != null) {
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
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView revenue;
        public TextView qty;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            revenue = (TextView) itemView.findViewById(R.id.revenue);
            qty = (TextView) itemView.findViewById(R.id.quantity);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            materialDialog.show();

            String[] toFrom = Myenum.INSTANCE.getToAndFromDate();

            Client.INSTANCE.getUserSales(MkShop.AUTH, toFrom[0], toFrom[1], leaderList.get(getAdapterPosition()).getUsername(), new Callback<List<Sales>>() {
                @Override
                public void success(List<Sales> sales, Response response) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                    String[] strings = new String[sales.size()];
                    for (int i = 0; i < sales.size(); i++) {
                        strings[i] = (sales.get(i).getCreated() + "     " + sales.get(i).getBrand() + " " + sales.get(i).getModel());
                    }

                    new MaterialDialog.Builder(context.getActivity())
                            .title("sales list")
                            .items(strings)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                }
                            })
                            .show();

                }

                @Override
                public void failure(RetrofitError error) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                    if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                        MkShop.toast(context.getActivity(), "please check your internet connection");
                    else MkShop.toast(context.getActivity(), error.getMessage());


                }
            });
        }
    }
}
