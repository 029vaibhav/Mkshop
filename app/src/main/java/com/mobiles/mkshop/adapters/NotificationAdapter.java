package com.mobiles.mkshop.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.pojos.Notification;

import java.util.List;

/**
 * Created by vaibhav on 26/7/15.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    Context context;
    List<Notification> notifications;


    public NotificationAdapter(Context context, List<Notification> notifications) {

        this.context = context;
        this.notifications = notifications;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Notification sales = notifications.get(position);
        holder.message.setText(sales.getMessage());
        holder.validity.setText("valid till " + sales.getEndDate());


    }

    @Override
    public int getItemCount() {
        if (notifications == null) {
            return 0;
        }
        return notifications.size();
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
