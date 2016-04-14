package com.mobiles.mkshop.adapters;


import android.app.Dialog;
import android.app.ProgressDialog;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.enums.ProductType;
import com.mobiles.mkshop.pojos.models.Leader;
import com.mobiles.mkshop.pojos.models.Sales;
import com.mobiles.mkshop.pojos.models.ServiceCenterEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 2/7/15.
 */
public class LeaderBoardItemAdapter extends RecyclerView.Adapter<LeaderBoardItemAdapter.ViewHolder> {

    Fragment context;
    String department;
    List<Leader> leaderList;
    ProgressDialog materialDialog;
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


        materialDialog = NavigationMenuActivity.materialDialog;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Leader leader = leaderList.get(position);
        holder.name.setText(leader.getUser().getName().toUpperCase(Locale.ENGLISH));


        if (department.equalsIgnoreCase("Sales")) {

            List<Sales> product = leader.getProduct();

            ArrayList<Sales> mobileSales = Lists.newArrayList(Iterables.filter(product, new Predicate<Sales>() {
                @Override
                public boolean apply(Sales input) {
                    if (input != null)
                        return input.getProductType().equals(ProductType.Mobile);
                    else return false;
                }
            }));

            ArrayList<Sales> accessorySales = Lists.newArrayList(Iterables.filter(product, new Predicate<Sales>() {
                @Override
                public boolean apply(Sales input) {
                    if (input != null)
                        return input.getProductType().equals(ProductType.Accessory);
                    else return false;
                }
            }));

            int mobileRevenue = 0;
            for (Sales sales : mobileSales) {
                mobileRevenue = mobileRevenue + Integer.parseInt(sales.getPrice());
            }
            int accessoryRevenue = 0;
            for (Sales sales : accessorySales) {
                accessoryRevenue = accessoryRevenue + Integer.parseInt(sales.getPrice());
            }

            holder.nameMobile.setText("Mobile");
            holder.qtyMobile.setText("" + mobileSales.size());
            holder.revenueMobile.setText(context.getString(R.string.rs) + " " + mobileRevenue);
            holder.linearLayout1.setVisibility(View.VISIBLE);
            holder.nameAccessory.setText("Accessory");
            holder.qtyAccessory.setText("" + accessorySales.size());
            holder.revenueAccessory.setText(context.getString(R.string.rs) + " " + accessoryRevenue);
            holder.linearLayout2.setVisibility(View.VISIBLE);
            holder.qty.setText("" + (mobileSales.size() + accessorySales.size()));
            holder.revenue.setText(context.getString(R.string.rs) + " " + (mobileRevenue + accessoryRevenue));

        } else {

            if (leader.getTechnicals() != null) {
                List<ServiceCenterEntity> technicals = leader.getTechnicals();

                int accessoryRevenue = 0;
                for (ServiceCenterEntity sales : technicals) {
                    accessoryRevenue = accessoryRevenue + sales.getPrice();
                }

                holder.nameMobile.setText("Mobile");
                holder.qtyMobile.setText("" + technicals.size());
                holder.revenueMobile.setText(context.getString(R.string.rs) + " " + accessoryRevenue);
                holder.qty.setText("" + technicals.size());
                holder.revenue.setText(context.getString(R.string.rs) + " " + accessoryRevenue);
            }
        }


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

                    if (department.equalsIgnoreCase("sales")) {
                        int a = lhs.getProduct().size();
                        int b = rhs.getProduct().size();
                        return a - b;
                    } else {
                        int a = lhs.getTechnicals().size();
                        int b = rhs.getTechnicals().size();
                        return a - b;
                    }
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

//        if (leaderList != null) {
//            Collections.sort(leaderList, new Comparator<Leader>() {
//                @Override
//                public int compare(Leader lhs, Leader rhs) {
//
//                    int a = Integer.parseInt(lhs.getPrice());
//                    int b = Integer.parseInt(rhs.getPrice());
//                    return a - b;
//                }
//            });
//
//            if (sort) {
//                leaderList = leaderList;
//
//            } else {
//
//
//                Collections.reverse(leaderList);
//
//            }
//            notifyDataSetChanged();
//        }
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

            if (department.equalsIgnoreCase("Sales")) {
                List<Sales> sales = leaderList.get(getAdapterPosition()).getProduct();
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
            } else {
                List<ServiceCenterEntity> sales = leaderList.get(getAdapterPosition()).getTechnicals();
                LeaderBoardTechDialogAdapter leaderBoardDialogAdapter = new LeaderBoardTechDialogAdapter(context, sales);
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
        }
    }
}
