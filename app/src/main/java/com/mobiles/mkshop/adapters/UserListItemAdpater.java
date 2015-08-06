package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.CalendarFragment;
import com.mobiles.mkshop.fragments.ProfileFragment;
import com.mobiles.mkshop.pojos.ExpenseEntity;
import com.mobiles.mkshop.pojos.PaymentType;
import com.mobiles.mkshop.pojos.UserListAttendance;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vaibhav on 4/7/15.
 */
public class UserListItemAdpater extends RecyclerView.Adapter<UserListItemAdpater.ViewHolder> {

    Fragment context;
    List<UserListAttendance> userListAttendances;


    public UserListItemAdpater(Fragment userListFragment, List<UserListAttendance> userListAttendances) {
        context = userListFragment;
        this.userListAttendances = userListAttendances;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(userListAttendances.get(position).getName());
        holder.mobile.setText(userListAttendances.get(position).getMobile());
        holder.attendance.setText(userListAttendances.get(position).getPresent() + "/" + userListAttendances.get(position).getTotalDay()
                + "-" + userListAttendances.get(position).getMonth());


        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = ProfileFragment.newInstance(userListAttendances.get(position).getUsername());
                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        holder.attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarFragment fragment = CalendarFragment.newInstance(userListAttendances.get(position).getUsername());
                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (userListAttendances != null)
            return userListAttendances.size();
        else return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView name;
        public TextView mobile;
        public TextView attendance;


        public ViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            attendance = (TextView) itemView.findViewById(R.id.attendace);

            itemView.setOnLongClickListener(this);


        }


        @Override
        public boolean onLongClick(View v) {
            new MaterialDialog.Builder(context.getActivity())
                    .title("Pay salary")
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input("", "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, final CharSequence input) {

                            ExpenseEntity expenseEntity = new ExpenseEntity();
                            expenseEntity.setPaymentType(PaymentType.Salary.name());
                            expenseEntity.setUsername(userListAttendances.get(getAdapterPosition()).getUsername());
                            expenseEntity.setAmount(input.toString());

                            Client.INSTANCE.payUserIncentive(MkShop.AUTH, expenseEntity, new Callback<String>() {
                                @Override
                                public void success(String s, Response response) {

                                    MkShop.toast(context.getActivity(), s);

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                    if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                        MkShop.toast(context.getActivity(), "please check your internet connection");


                                }
                            });
                        }
                    }).show();
            return false;
        }
    }
}
