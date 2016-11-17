package com.mobiles.msm.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.mobiles.msm.application.Myenum;
import com.mobiles.msm.fragments.RepairListItemFragment;
import com.mobiles.msm.pojos.models.ServiceCenterEntity;
import com.mobiles.msm.utils.Utilities;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 28/6/15.
 */
public class ServiceCenterAdapter extends RecyclerView.Adapter<ServiceCenterAdapter.ViewHolder> {

    Fragment context;
    List<ServiceCenterEntity> repairList;

    public ServiceCenterAdapter(Fragment context, List<ServiceCenterEntity> repairList) {
        this.context = context;
        this.repairList = repairList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        ServiceCenterEntity serviceCenterEntity = repairList.get(position);

        viewHolder.modelNo.setText(serviceCenterEntity.getBrand() + " " + serviceCenterEntity.getModel());
        String date = Utilities.formatDate(serviceCenterEntity.getCreated());
        viewHolder.date.setText(date);

        if (serviceCenterEntity.getStatus().equalsIgnoreCase("done")) {
            viewHolder.status.setTextColor(ContextCompat.getColor(context.getActivity(), R.color.flatGreen));
        }
        viewHolder.status.setText(serviceCenterEntity.getStatus());

        viewHolder.jobNo.setText(serviceCenterEntity.getJobNo());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return repairList.size();
    }


    public void filter(final Editable s) {


        if (s.length() > 0) {
            Collection<ServiceCenterEntity> serviceCenterEntityList = Collections2.filter(Myenum.INSTANCE.getServiceList(null),
                    new Predicate<ServiceCenterEntity>() {
                        @Override
                        public boolean apply(ServiceCenterEntity input) {
                            return (input.getBrand().toLowerCase(Locale.ENGLISH).contains(s) || input.getModel().toLowerCase(Locale.ENGLISH).contains(s) || input.getJobNo().toLowerCase(Locale.ENGLISH).contains(s)
                                    || input.getCreated().toLowerCase(Locale.ENGLISH).contains(s) || input.getStatus().toLowerCase(Locale.ENGLISH).contains(s));
                        }
                    });
            repairList = Lists.newArrayList(serviceCenterEntityList);


        } else {

            repairList = Myenum.INSTANCE.getServiceList(null);

        }
        notifyDataSetChanged();

    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView modelNo, date, status, jobNo;

        public ViewHolder(View convertView) {
            super(convertView);
            modelNo = (TextView) convertView.findViewById(R.id.repairmodel);
            date = (TextView) convertView.findViewById(R.id.repairdate);
            status = (TextView) convertView.findViewById(R.id.reapirstatus);
            jobNo = (TextView) convertView.findViewById(R.id.repairjobno);
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Myenum.INSTANCE.setServiceCenterEntity(repairList.get(getAdapterPosition()));
            Fragment fragment = new RepairListItemFragment();
            context.getFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

        }
    }

}
