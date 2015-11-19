package com.mobiles.mkshop.adapters;


import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.Leader;
import com.mobiles.mkshop.pojos.LeaderBoardDetails;

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
    Dialog dialog;
    View view;
    ImageView imageView, backButton;
    RecyclerView recyclerView;

    public LeaderBoardItemAdapter(Fragment salesReportList, String department) {
        context = salesReportList;
        this.department = department;
        leaderList = Myenum.INSTANCE.getLeaderList(department);


        dialog = new Dialog(context.getActivity(), R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.leaderboard_dialog_list);


        imageView = (ImageView) dialog.findViewById(R.id.bill_image);
        backButton = (ImageView) dialog.findViewById(R.id.back_button);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


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

        if (leader.getProductDetail() != null) {

            try {
                holder.nameMobile.setText(leader.getProductDetail().get(0).getProductType());
                holder.qtyMobile.setText(leader.getProductDetail().get(0).getQuantity());
                holder.revenueMobile.setText(context.getString(R.string.rs) + " " + leader.getProductDetail().get(0).getPrice());
                holder.linearLayout1.setVisibility(View.VISIBLE);

            } catch (Exception e) {

            }

            try {

                holder.nameAccessory.setText(leader.getProductDetail().get(1).getProductType());
                holder.qtyAccessory.setText(leader.getProductDetail().get(1).getQuantity());
                holder.revenueAccessory.setText(context.getString(R.string.rs) + " " + leader.getProductDetail().get(1).getPrice());

                holder.linearLayout2.setVisibility(View.VISIBLE);

            } catch (Exception e) {

            }


        }
        holder.qty.setText(leader.getQuantity());
        holder.revenue.setText(context.getString(R.string.rs) + " " + leader.getPrice());


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

        public TextView name, nameMobile, nameAccessory;
        public TextView revenue, revenueMobile, revenueAccessory;
        public TextView qty, qtyMobile, qtyAccessory;
        public LinearLayout linearLayout1, linearLayout2;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            nameMobile = (TextView) itemView.findViewById(R.id.nameMobile);
            nameAccessory = (TextView) itemView.findViewById(R.id.nameAccessory);


            revenue = (TextView) itemView.findViewById(R.id.revenue);
            revenueMobile = (TextView) itemView.findViewById(R.id.revenueMobile);
            revenueAccessory = (TextView) itemView.findViewById(R.id.revenueAccessory);
            qty = (TextView) itemView.findViewById(R.id.quantity);
            qtyMobile = (TextView) itemView.findViewById(R.id.quantityMobile);
            qtyAccessory = (TextView) itemView.findViewById(R.id.quantityAccessory);

            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linear2);
            linearLayout2 = (LinearLayout) itemView.findViewById(R.id.linear3);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            materialDialog.show();

            String[] toFrom = Myenum.INSTANCE.getToAndFromDate();

            Client.INSTANCE.getUserSales(MkShop.AUTH, toFrom[0], toFrom[1], leaderList.get(getAdapterPosition()).getUsername(), department, new Callback<List<LeaderBoardDetails>>() {
                @Override
                public void success(List<LeaderBoardDetails> sales, Response response) {

                    if (materialDialog != null && materialDialog.isShowing())
                        materialDialog.dismiss();

                    LeaderBoardDialogAdapter leaderBoardDialogAdapter = new LeaderBoardDialogAdapter(context, sales);
                    recyclerView.setAdapter(leaderBoardDialogAdapter);
                    backButton.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          dialog.dismiss();
                                                      }
                                                  }
                    );
                    dialog.show();

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
