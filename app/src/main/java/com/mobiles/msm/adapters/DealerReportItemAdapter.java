package com.mobiles.msm.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.mobiles.msm.R;
import com.mobiles.msm.activities.NavigationMenuActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.application.Myenum;
import com.mobiles.msm.pojos.enums.TransactionType;
import com.mobiles.msm.pojos.models.Payment;
import com.mobiles.msm.pojos.models.Purchase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by vaibhav on 2/7/15.
 */
public class DealerReportItemAdapter extends RecyclerView.Adapter<DealerReportItemAdapter.ViewHolder> {

    Fragment context;
    TransactionType transactionType;
    List<Purchase> purchaseHistories;
    List<Payment> paymentHistories;

    Dialog dialog;
    ProgressDialog progressMaterialDialog;
    ImageView imageView, backButton;
    ProgressBar progressBar;

    DateTimeFormatter formatter;


    public DealerReportItemAdapter(Fragment salesReportList, TransactionType transactionType) {
        context = salesReportList;
        formatter = DateTimeFormat.forPattern(context.getString(R.string.date_format));
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

            Payment payment = paymentHistories.get(position);
            holder.amount.setText(context.getActivity().getString(R.string.rs) + " " + payment.getAmount());
            holder.date.setText(formatter.parseDateTime(payment.getCreated()).toString("dd/MM/YY"));
            holder.note.setText(context.getActivity().getString(R.string.rs) + " " + payment.getNote());
            holder.month.setText(formatter.parseDateTime(payment.getCreated()).toString("MMM"));


        } else {
            purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();

            Purchase paymentHistory = purchaseHistories.get(position);
            holder.amount.setText(context.getActivity().getString(R.string.rs) + " " + paymentHistory.getAmount());
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

                Collection<Payment> filter = Collections2.filter(paymentHistories, new Predicate<Payment>() {
                    @Override
                    public boolean apply(Payment input) {
                        return (input.getCreated().toLowerCase(Locale.ENGLISH).contains(s.toLowerCase()) ||
                                input.getNote().toLowerCase(Locale.ENGLISH).contains(s.toLowerCase()) ||
                                String.valueOf(input.getAmount()).toLowerCase(Locale.ENGLISH).contains(s.toLowerCase())
                                || formatter.parseDateTime(input.getCreated()).toString("MMM").toLowerCase().contains(s.toLowerCase()));
                    }
                });
                paymentHistories = Lists.newArrayList(filter);
            } else paymentHistories = Myenum.INSTANCE.getPaymentHistories();
        } else {
            if (s.length() > 0) {
                Collection<Purchase> filter = Collections2.filter(purchaseHistories, new Predicate<Purchase>() {
                    @Override
                    public boolean apply(Purchase input) {
                        return (input.getCreated().toLowerCase(Locale.ENGLISH).contains(s.toLowerCase()) ||
                                input.getNote().toLowerCase(Locale.ENGLISH).contains(s.toLowerCase()) ||
                                String.valueOf(input.getAmount()).toLowerCase(Locale.ENGLISH).contains(s.toLowerCase()));
                    }
                });
                purchaseHistories = Lists.newArrayList(filter);
            } else
                purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();
        }

        notifyAll();


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

            AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
            builder.setTitle("Select");
            builder.setItems(R.array.purchase_options, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog1, int which) {

                    dialog1.dismiss();
                    Long serverId = null;
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
                                        MyApplication.toast(context.getActivity(), "unable to load image");
                                    }
                                });

                            } else {
                                MyApplication.toast(context.getActivity(), "Not applicable");
                            }
                            break;
                        case 1: // call api to delete
                            callDeleteApi(serverId);
                            break;

                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        private void callDeleteApi(final Long serverId) {

            if (transactionType == TransactionType.Payment) {

                Client.INSTANCE.deletePayment(MyApplication.AUTH, serverId).enqueue(new retrofit2.Callback<Payment>() {
                    @Override
                    public void onResponse(Call<Payment> call, Response<Payment> response) {
                        MyApplication.toast(context.getActivity(), "payment successfully deleted");
                        if (transactionType == TransactionType.Payment) {
                            try {
                                Payment payment = Payment.find(Payment.class, "server_id = ?", String.valueOf(serverId)).get(0);
                                Payment.deleteAll(Payment.class, "server_id = ?", String.valueOf(serverId));
                                if (payment != null) {
                                    List<Payment> paymentHistoriesNew = Payment.find(Payment.class, "dealer_id =?", String.valueOf(payment.getDealerId()));
                                    Myenum.INSTANCE.setPaymentHistories(paymentHistoriesNew);
                                    paymentHistories = Myenum.INSTANCE.getPaymentHistories();
                                }
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Payment> call, Throwable t) {
                            MyApplication.toast(context.getActivity(), t.getMessage());
                    }
                });
            } else {
                Client.INSTANCE.deletePurchase(MyApplication.AUTH, serverId).enqueue(new retrofit2.Callback<Purchase>() {
                    @Override
                    public void onResponse(Call<Purchase> call, Response<Purchase> response) {
                        MyApplication.toast(context.getActivity(), "Purchase deleted successfully");
                        if (transactionType == TransactionType.Purchase) {
                            try {
                                Purchase purchase = Purchase.find(Purchase.class, "server_id = ?", String.valueOf(serverId)).get(0);
                                Purchase.deleteAll(Purchase.class, "server_id = ?", String.valueOf(serverId));
                                if (purchase != null) {
                                    List<Purchase> newPurchaseHistories = Purchase.find(Purchase.class, "dealer_id = ?", String.valueOf(purchase.getDealerId()));
                                    Myenum.INSTANCE.setPurchaseHistories(newPurchaseHistories);
                                    purchaseHistories = Myenum.INSTANCE.getPurchaseHistories();
                                }
                            } catch (IndexOutOfBoundsException e) {
                            }
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Purchase> call, Throwable t) {
                            MyApplication.toast(context.getActivity(), t.getMessage());

                    }
                });
            }

        }
    }
}

