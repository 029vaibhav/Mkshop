package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.Incentive;
import com.mobiles.mkshop.fragments.IncentiveUserListFragment;
import com.mobiles.mkshop.pojos.models.IncentiveEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

                new MaterialDialog.Builder(context.getActivity())
                        .items(R.array.delete)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                Client.INSTANCE.deleteIncentiveMessage(MkShop.AUTH, incentiveEntity.getId(), "delete", new Callback<String>() {
                                    @Override
                                    public void success(String s, Response response) {

                                        // MkShop.toast(context.getActivity(), s);
                                        Toast.makeText(context.getActivity(), s, Toast.LENGTH_SHORT).show();
                                        Fragment fragment = new Incentive();

                                        context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                        if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                            MkShop.toast(context.getActivity(), "please check your internet connection");
                                        else
                                            MkShop.toast(context.getActivity(), error.getMessage());


                                    }
                                });
                            }
                        }).show();
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
