package com.mobiles.mkshop.adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.ProductExpense;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vaibhav on 3/10/15.
 */
public class ViewBillAdapter extends RecyclerView.Adapter<ViewBillAdapter.ViewHolder> {

    List<ProductExpense> productExpenseList;
    Fragment context;
    Dialog dialog;
    View view;
    ImageView imageView, backButton;
    RecyclerView recyclerView;

    MaterialDialog progressMaterialDialog;


    public ViewBillAdapter(Fragment userListFragment, List<ProductExpense> productExpenseList) {
        this.context = userListFragment;
        this.productExpenseList = productExpenseList;

        dialog = new Dialog(context.getActivity(), R.style.AppTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_bill_dialog);

        progressMaterialDialog = new MaterialDialog.Builder(context.getActivity())
                .progress(true, 0)
                .cancelable(false)
                .build();


        imageView = (ImageView) dialog.findViewById(R.id.bill_image);
        backButton = (ImageView) dialog.findViewById(R.id.back_button);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bill_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewBillAdapter.ViewHolder holder, int position) {


        ProductExpense productExpense = productExpenseList.get(position);

        holder.date.setText(productExpense.getCreated());
        holder.dealerName.setText(productExpense.getDealerName());
        holder.totalAmount.setText("Total " + context.getString(R.string.rs) + " " + productExpense.getTotalAmt());

        if (productExpense.getDueAmount() == 0) {
            holder.dueAmount.setTextColor(Color.GREEN);
            holder.dueAmount.setText("paid");
        } else {
            holder.dueAmount.setTextColor(Color.RED);
            holder.dueAmount.setText("Due " + context.getString(R.string.rs) + " " + productExpense.getDueAmount());
        }


    }

    @Override
    public int getItemCount() {
        return productExpenseList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dealerName, totalAmount, dueAmount, date;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            dealerName = (TextView) itemView.findViewById(R.id.dealer_name);
            totalAmount = (TextView) itemView.findViewById(R.id.total_amt);
            dueAmount = (TextView) itemView.findViewById(R.id.due_amt);
            date = (TextView) itemView.findViewById(R.id.date);
            cardView = (CardView) itemView.findViewById(R.id.cardlist_item);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            new MaterialDialog.Builder(context.getActivity())
                    .items(R.array.bill_action)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                            switch (i) {
                                case 0:

                                    materialDialog.dismiss();
                                    ViewBillDialogAdapter viewBillDialogAdapter = new ViewBillDialogAdapter(context, productExpenseList.get(getAdapterPosition()).getEntries(productExpenseList.get(getAdapterPosition()).getServerId()));
                                    recyclerView.setAdapter(viewBillDialogAdapter);
                                    backButton.setOnClickListener(new View.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(View v) {
                                                                          dialog.dismiss();
                                                                      }
                                                                  }
                                    );
                                    dialog.show();
                                    Picasso.with(context.getActivity()).load(productExpenseList.get(getAdapterPosition()).getImage()).into(imageView);


                                    break;
                                case 1:

                                    materialDialog.dismiss();
                                    new MaterialDialog.Builder(context.getActivity())
                                            .title("Pay Due Amount")
                                            .inputType(InputType.TYPE_CLASS_NUMBER)
                                            .input("due amount", "", new MaterialDialog.InputCallback() {
                                                @Override
                                                public void onInput(final MaterialDialog dialog, final CharSequence input) {


                                                    if (input.length() != 0 && Integer.parseInt(input.toString()) <= productExpenseList.get(getAdapterPosition()).getDueAmount()) {

                                                        progressMaterialDialog.show();
                                                        Client.INSTANCE.duePayment(MkShop.AUTH, productExpenseList.get(getAdapterPosition()).getServerId(), input.toString(), new Callback<String>() {
                                                            @Override
                                                            public void success(String s, Response response) {

                                                                if (dialog != null && dialog.isShowing())
                                                                    dialog.dismiss();

                                                                if (progressMaterialDialog != null && progressMaterialDialog.isShowing())
                                                                    progressMaterialDialog.dismiss();
                                                                MkShop.toast(context.getActivity(), s);


                                                            }

                                                            @Override
                                                            public void failure(RetrofitError error) {
                                                                if (progressMaterialDialog != null && progressMaterialDialog.isShowing())
                                                                    progressMaterialDialog.dismiss();
                                                                if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                                                    MkShop.toast(context.getActivity(), "please check your internet connection");


                                                            }
                                                        });
                                                    } else {
                                                        MkShop.toast(context.getActivity(), "amount cant be greater than due amount or blank");
                                                    }
                                                }
                                            }).show();

                                    break;

                                case 2:

                                    break;

                            }


                        }
                    }).show();


        }
    }
}
