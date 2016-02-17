package com.mobiles.mkshop.adapters;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.activities.NavigationMenuActivity;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.pojos.enums.TransactionType;
import com.mobiles.mkshop.pojos.models.PaymentHistory;
import com.mobiles.mkshop.pojos.models.PurchaseHistory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vaibhav on 2/7/15.
 */
public class DealerReportItemAdapter extends RecyclerView.Adapter<DealerReportItemAdapter.ViewHolder> {

    Fragment context;
    TransactionType transactionType;
    List<PurchaseHistory> purchaseHistories;
    List<PaymentHistory> paymentHistories;

    Dialog dialog;
    MaterialDialog progressMaterialDialog;
    ImageView imageView, backButton;
    ProgressBar progressBar;

    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");


    public DealerReportItemAdapter(Fragment salesReportList, TransactionType transactionType) {
        context = salesReportList;
        this.transactionType = transactionType;
        if (transactionType == TransactionType.Payment) {
            paymentHistories = Myenum.INSTANCE.getPaymentHistories();
        } else {
            purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();
        }

        dialog = new Dialog(context.getActivity(), R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_bill_dialog);

        progressMaterialDialog = NavigationMenuActivity.materialDialog;


        imageView = (ImageView) dialog.findViewById(R.id.bill_image);
        backButton = (ImageView) dialog.findViewById(R.id.back_button);
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (transactionType == TransactionType.Payment) {
            paymentHistories = Myenum.INSTANCE.getPaymentHistories();

            PaymentHistory paymentHistory = paymentHistories.get(position);
            holder.amount.setText(context.getActivity().getString(R.string.rs) + " " + paymentHistory.getAmount());
            holder.date.setText(formatter.parseDateTime(paymentHistory.getCreated()).toString("dd/MM/YY"));
            holder.note.setText(context.getActivity().getString(R.string.rs) + " " + paymentHistory.getNote());
            holder.month.setText(formatter.parseDateTime(paymentHistory.getCreated()).toString("MMM"));


        } else {
            purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();

            PurchaseHistory paymentHistory = purchaseHistories.get(position);
            holder.amount.setText(context.getActivity().getString(R.string.rs) + " " + paymentHistory.getTotalAmt());
            holder.date.setText(formatter.parseDateTime(paymentHistory.getCreated()).toString("dd/MM/YY"));
            holder.note.setText(paymentHistory.getNote());
            holder.month.setText(formatter.parseDateTime(paymentHistory.getCreated()).toString("MMM"));
        }


    }


    @Override
    public int getItemCount() {
        if (transactionType == TransactionType.Payment) {
            return paymentHistories.size();
        } else {
            return purchaseHistories.size();
        }
    }

    public void setSearchText(Editable s1) {

        final String s = s1.toString().toLowerCase(Locale.ENGLISH);
        if (transactionType == TransactionType.Payment) {


            if (s.length() > 0) {
                Collection<PaymentHistory> filter = Collections2.filter(paymentHistories, new Predicate<PaymentHistory>() {
                    @Override
                    public boolean apply(PaymentHistory input) {


                        return (input.getCreated().toLowerCase(Locale.ENGLISH).contains(s) ||
                                input.getNote().toLowerCase(Locale.ENGLISH).contains(s) ||
                                input.getAmount().toLowerCase(Locale.ENGLISH).contains(s));
                    }
                });
                paymentHistories = Lists.newArrayList(filter);
            } else paymentHistories = Myenum.INSTANCE.getPaymentHistories();


        } else {
            if (s.length() > 0) {
                Collection<PurchaseHistory> filter = Collections2.filter(purchaseHistories, new Predicate<PurchaseHistory>() {
                    @Override
                    public boolean apply(PurchaseHistory input) {


                        return (input.getCreated().toLowerCase(Locale.ENGLISH).contains(s) ||
                                input.getNote().toLowerCase(Locale.ENGLISH).contains(s) ||
                                input.getTotalAmt().toLowerCase(Locale.ENGLISH).contains(s));
                    }
                });
                purchaseHistories = Lists.newArrayList(filter);
            } else
                purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();
        }

        notifyDataSetChanged();


    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardItemLayout;
        public TextView amount, date, note, month;

        public ViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

            amount = (TextView) itemView.findViewById(R.id.amount);
            date = (TextView) itemView.findViewById(R.id.date);
            note = (TextView) itemView.findViewById(R.id.note);
            month = (TextView) itemView.findViewById(R.id.month);

            cardItemLayout.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            showDialog();
        }


        private void showDialog() {
            new MaterialDialog.Builder(context.getActivity())
                    .items(R.array.purchase_options)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                                       @Override
                                       public void onSelection(final MaterialDialog dialog1, View view, int which, CharSequence text) {

                                           if (dialog1 != null && dialog1.isShowing())
                                               dialog1.dismiss();
                                           String serverId = null;
                                           if (transactionType == TransactionType.Purchase)
                                               serverId = purchaseHistories.get(getAdapterPosition()).getServerId();
                                           else if (transactionType == TransactionType.Payment)
                                               serverId = paymentHistories.get(getAdapterPosition()).getServerId();


                                           switch (which) {
                                               case 0: //view profile
                                                   if (transactionType == TransactionType.Purchase) {
                                                       backButton.setOnClickListener(new View.OnClickListener() {
                                                                                         @Override
                                                                                         public void onClick(View v) {
                                                                                             if (dialog != null && dialog.isShowing())
                                                                                                 dialog.dismiss();
                                                                                         }
                                                                                     }
                                                       );
                                                       dialog.show();
                                                       Picasso.with(context.getActivity()).load(purchaseHistories.get(getAdapterPosition()).getImage()).into(imageView, new Callback() {
                                                           @Override
                                                           public void onSuccess() {
                                                               progressBar.setVisibility(View.GONE);
                                                           }

                                                           @Override
                                                           public void onError() {

                                                               progressBar.setVisibility(View.GONE);
                                                               MkShop.toast(context.getActivity(), "unable to load image");
                                                           }
                                                       });

                                                   } else {
                                                       MkShop.toast(context.getActivity(), "Not applicable");
                                                   }
                                                   break;
                                               case 1: // call api to delete
                                                   callDeleteApi(serverId);
                                                   break;

                                           }


                                       }
                                   }

                    )
                    .

                            show();

        }

        private void callDeleteApi(final String serverId) {


            Client.INSTANCE.deletePayment(MkShop.AUTH, transactionType.name(), serverId, new retrofit.Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    MkShop.toast(context.getActivity(), s);


                    if (transactionType == TransactionType.Purchase) {

                        try {
                            PurchaseHistory purchaseHistory = PurchaseHistory.find(PurchaseHistory.class, "server_id = ?", serverId).get(0);
                            PurchaseHistory.deleteAll(PurchaseHistory.class, "server_id = ?", serverId);
                            if (purchaseHistory != null) {
                                List<PurchaseHistory> newPurchaseHistories = PurchaseHistory.find(PurchaseHistory.class, "dealer_name = ?", purchaseHistory.getDealerName());
                                Myenum.INSTANCE.setPurchaseHistories(newPurchaseHistories);
                                purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();
                            }
                        } catch (IndexOutOfBoundsException e) {
                        }
                    } else if (transactionType == TransactionType.Payment) {

                        try {
                            PaymentHistory paymentHistory = PaymentHistory.find(PaymentHistory.class, "server_id = ?", serverId).get(0);
                            PaymentHistory.deleteAll(PaymentHistory.class, "server_id = ?", serverId);
                            if (paymentHistory != null) {
                                List<PaymentHistory>paymentHistoriesNew = PaymentHistory.find(PaymentHistory.class, "dealer_id =?", paymentHistory.getDealerId());
                                Myenum.INSTANCE.setPaymentHistories(paymentHistoriesNew);
                                paymentHistories = Myenum.INSTANCE.getPaymentHistories();

                            }
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                    notifyDataSetChanged();

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
}
