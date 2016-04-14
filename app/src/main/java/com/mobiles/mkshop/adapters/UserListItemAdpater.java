package com.mobiles.mkshop.adapters;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.ProfileFragment;
import com.mobiles.mkshop.fragments.UserListFragment;
import com.mobiles.mkshop.pojos.enums.PaymentType;
import com.mobiles.mkshop.pojos.models.ExpenseEntity;
import com.mobiles.mkshop.pojos.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vaibhav on 4/7/15.
 */
public class UserListItemAdpater extends RecyclerView.Adapter<UserListItemAdpater.ViewHolder> {

    Fragment context;
    List<User> userListAttendances;


    public UserListItemAdpater(Fragment userListFragment, List<User> userListAttendances) {
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
//        holder.attendance.setText(userListAttendances.get(position).getPresent() + "/" + userListAttendances.get(position).getTotalDay()
//                + "-" + userListAttendances.get(position).getMonth());


//        holder.name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProfileFragment fragment = ProfileFragment.newInstance(userListAttendances.get(position).getUsername());
//                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//            }
//        });
//
//        holder.attendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CalendarFragment fragment = CalendarFragment.newInstance(userListAttendances.get(position).getUsername());
//                context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (userListAttendances != null)
            return userListAttendances.size();
        else return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView mobile;
        public TextView attendance;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            attendance = (TextView) itemView.findViewById(R.id.attendace);
            cardView = (CardView) itemView.findViewById(R.id.cardlist_item);

            cardView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
            builder.setTitle("Select");
            builder.setItems(R.array.userActions, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog1, int which) {

                    dialog1.dismiss();
                    final String username = userListAttendances.get(getAdapterPosition()).getUsername();

                    switch (which) {
                        case 0: //view profile

                            ProfileFragment fragment = ProfileFragment.newInstance(username);
                            context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                            break;
                        case 1: // view attendance

                            MkShop.toast(context.getActivity(),"this feature has been disable");
//                            CalendarFragment calendarFragment = CalendarFragment.newInstance(username);
//                            context.getFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).addToBackStack(null).commit();
                            break;
                        case 2: // pay salary

                            showPaySalaryDialog(username);
                            break;
                        case 3: // delete user
                            Client.INSTANCE.deleteUser(MkShop.AUTH, userListAttendances.get(getAdapterPosition()).getUsername()).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    MkShop.toast(context.getActivity(), "user sucessfully deleted");

                                    UserListFragment fragment = UserListFragment.newInstance();
                                    context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                        MkShop.toast(context.getActivity(), t.getMessage());
                                }
                            });

                            break;
                    }


                }
            });
            AlertDialog alert = builder.create();
            alert.show();


        }

        private void showPaySalaryDialog(final String username) {

            final AlertDialog.Builder inputAlert = new AlertDialog.Builder(context.getActivity());
            inputAlert.setTitle("Pay salary");
            final EditText userInput = new EditText(context.getActivity());
            userInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            inputAlert.setView(userInput);
            inputAlert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (userInput.length() != 0) {

                        ExpenseEntity expenseEntity = new ExpenseEntity();
                        expenseEntity.setPaymentType(PaymentType.Salary.name());
                        expenseEntity.setUsername(username);
                        expenseEntity.setAmount(userInput.getText().toString());

                        Client.INSTANCE.payUserIncentive(MkShop.AUTH, expenseEntity).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                MkShop.toast(context.getActivity(), "success");

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                                    MkShop.toast(context.getActivity(), t.getMessage());
                            }
                        });
                    } else {
                        MkShop.toast(context.getActivity(), "please enter some amount");
                    }
                }
            });
            inputAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = inputAlert.create();
            alertDialog.show();
        }
    }
}
