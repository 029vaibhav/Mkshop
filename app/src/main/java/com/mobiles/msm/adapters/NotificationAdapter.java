package com.mobiles.msm.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.msm.R;
import com.mobiles.msm.pojos.models.Message;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 26/7/15.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    Context context;
    List<Message> messages;
    List<Message> dummyMessages;


    public NotificationAdapter(Context context, List<Message> messages) {

        this.context = context;
        this.messages = messages;
        this.dummyMessages = messages;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Message sales = messages.get(position);
        holder.message.setText(sales.getMessage());
        holder.validity.setText("valid till " + sales.getEndDate());


    }

    @Override
    public int getItemCount() {
        if (messages == null) {
            return 0;
        }
        return messages.size();
    }

    public void filter(final Editable s) {


        if (s.length() > 0) {
            Collection<Message> salesCollection = Collections2.filter(messages,
                    new Predicate<Message>() {
                        @Override
                        public boolean apply(Message input) {
                            return (input.getMessage().toLowerCase(Locale.ENGLISH).contains(s.toString()));
                        }
                    });

            messages = Lists.newArrayList(salesCollection);


        } else {

            messages = dummyMessages;

        }
        notifyDataSetChanged();

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
