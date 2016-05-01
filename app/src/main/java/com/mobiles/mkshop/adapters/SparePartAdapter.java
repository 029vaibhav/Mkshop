package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.fragments.SparePartListItemFragment;
import com.mobiles.mkshop.pojos.enums.UserType;
import com.mobiles.mkshop.pojos.models.SparePart;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vaibhav on 29/6/15.
 */
public class SparePartAdapter extends RecyclerView.Adapter<SparePartAdapter.ViewHolder> {

    Fragment context;
    List<SparePart> sparePartList;
    ArrayList<SparePart> sparePartArrayList;
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
    DateTime dt;

    public SparePartAdapter(Fragment context, List<SparePart> sparePartList) {
        this.context = context;
        this.sparePartList = sparePartList;
        sparePartArrayList = new ArrayList<>();
        sparePartArrayList.addAll(sparePartList);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.part_request_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SparePart sparePart = sparePartList.get(position);
        holder.type.setText(sparePart.getType());
        holder.brand.setText(sparePart.getBrand());
        holder.compatibleModel.setText(sparePart.getCompatibleMobile());
        holder.quantity.setText("" + sparePart.getQuantity());


    }

    @Override
    public int getItemCount() {
        return sparePartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView type, brand, compatibleModel, quantity;

        public ViewHolder(View convertView) {
            super(convertView);
            type = (TextView) convertView.findViewById(R.id.type);
            brand = (TextView) convertView.findViewById(R.id.brand);
            compatibleModel = (TextView) convertView.findViewById(R.id.compatible_model);
            quantity = (TextView) convertView.findViewById(R.id.quantity);

            if (MkShop.Role != null && UserType.valueOf(MkShop.Role.toUpperCase()) != UserType.TECHNICIAN)
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = SparePartListItemFragment.newInstance(sparePartList.get(getAdapterPosition()).getId());
                        context.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                    }
                });
        }
    }

    public void Filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        sparePartList.clear();
        if (charText.length() == 0) {
            sparePartList.addAll(sparePartArrayList);
        } else {
            for (SparePart wp : sparePartArrayList) {
                if (wp.getBrand().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getCompatibleMobile().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getType().toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(wp.getQuantity()).toLowerCase(Locale.getDefault()).contains(charText)
                        ) {
                    sparePartList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
