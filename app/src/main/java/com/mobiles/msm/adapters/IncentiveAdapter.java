package com.mobiles.msm.adapters;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.fragments.Incentive;
import com.mobiles.msm.fragments.IncentiveUserListFragment;
import com.mobiles.msm.pojos.models.IncentiveEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 26/7/15.
 */
public class IncentiveAdapter extends RecyclerView.Adapter<IncentiveAdapter.ViewHolder> {


    Fragment context;
    List<IncentiveEntity> incentiveEntities;


    public IncentiveAdapter(Fragment context, List<IncentiveEntity> incentiveEntities) {

        this.context = context;
        this.incentiveEntities = incentiveEntities;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final IncentiveEntity incentiveEntity = incentiveEntities.get(position);
        holder.message.setText(incentiveEntity.getBrand() + " " + incentiveEntity.getModel() + " with base price Rs " + incentiveEntity.getBasePrice()
                + " and incentive amount Rs " + incentiveEntity.getIncentiveAmount());
        holder.validity.setText("valid till " + incentiveEntity.getValidity());


        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IncentiveUserListFragment fragment = IncentiveUserListFragment.newInstance(holder.message.getText().toString(), "" + incentiveEntity.getId());
                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

            }
        });

        holder.message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setTitle("Select");
                builder.setItems(R.array.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        Client.INSTANCE.deleteIncentiveMessage(MyApplication.AUTH, incentiveEntity.getId()).enqueue(new Callback<IncentiveEntity>() {
                            @Override
                            public void onResponse(Call<IncentiveEntity> call, Response<IncentiveEntity> response) {
                                MyApplication.toast(context.getActivity(), "Deleted Successfully");
                                Fragment fragment = new Incentive();
                                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                            }

                            @Override
                            public void onFailure(Call<IncentiveEntity> call, Throwable t) {

                                    MyApplication.toast(context.getActivity(), t.getMessage());


                            }
                        });
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        if (incentiveEntities == null) {
            return 0;
        }
        return incentiveEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardItemLayout;
        public TextView message;
        public TextView validity;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            message = (TextView) itemView.findViewById(R.id.message);
            validity = (TextView) itemView.findViewById(R.id.validity);


        }


    }
}
