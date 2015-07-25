package com.mobiles.mkshop.Adapters;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.Fragments.CalendarFragment;
import com.mobiles.mkshop.Fragments.ProfileFragment;
import com.mobiles.mkshop.Pojos.UserListAttendance;
import com.mobiles.mkshop.R;

import java.util.List;

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
                CalendarFragment fragment = CalendarFragment.newInstance(userListAttendances.get(position).getUsername());;
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


    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView mobile;
        public TextView attendance;


        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            mobile = (TextView) itemView.findViewById(R.id.mobile);
            attendance = (TextView) itemView.findViewById(R.id.attendace);


        }


    }
}
