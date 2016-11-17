package com.mobiles.msm.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.enums.UserType;
import com.mobiles.msm.pojos.models.Profile;
import com.mobiles.msm.pojos.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.VersionViewHolder> {

    Context context;
    List<Profile> list;
    OnItemClickListener clickListener;
    String username;


    public ProfileAdapter(String username, Context context, List<Profile> list) {
        this.list = list;
        this.context = context;
        this.username = username;

    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_list_item, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, int i) {


        if (!list.get(i).getTitle().equalsIgnoreCase("photo")) {
            versionViewHolder.title.setText(list.get(i).getTitle().toUpperCase(Locale.ENGLISH));
            versionViewHolder.subTitle.setText(list.get(i).getvalue());
            if (!MyApplication.Role.equalsIgnoreCase(UserType.ADMIN.name()) && versionViewHolder.title.getText().toString().equalsIgnoreCase("role"))
                versionViewHolder.imageView.setVisibility(View.INVISIBLE);
            else if (versionViewHolder.title.getText().toString().equalsIgnoreCase("username"))
                versionViewHolder.imageView.setVisibility(View.INVISIBLE);
            else
                versionViewHolder.imageView.setVisibility(View.VISIBLE);

            versionViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("edit " + versionViewHolder.title.getText());
                    final EditText input = new EditText(context);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.length() != 0) {
                                Map<String, String> map = new HashMap();
                                map.put(versionViewHolder.title.getText().toString().toLowerCase(), input.getText().toString());
                                Client.INSTANCE.updateProfile(MyApplication.AUTH, username, map).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        MyApplication.toast(context, "updated successfully");
                                        versionViewHolder.subTitle.setText(input.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        MyApplication.toast(context, t.getMessage());
                                    }
                                });
                            } else {
                                MyApplication.toast(context, "please enter some text");
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });


        } else {
            versionViewHolder.title.setVisibility(View.GONE);
            versionViewHolder.subTitle.setVisibility(View.GONE);
            versionViewHolder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardItemLayout;
        TextView title;
        EditText subTitle;
        ImageView imageView;

        public VersionViewHolder(View itemView) {
            super(itemView);

            cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            subTitle = (EditText) itemView.findViewById(R.id.detail);
            imageView = (ImageView) itemView.findViewById(R.id.edit);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(v, getPosition());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
