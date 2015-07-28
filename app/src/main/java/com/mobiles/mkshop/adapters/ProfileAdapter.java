package com.mobiles.mkshop.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mobiles.mkshop.R;
import com.mobiles.mkshop.application.Client;
import com.mobiles.mkshop.application.MkShop;
import com.mobiles.mkshop.pojos.Profile;
import com.mobiles.mkshop.pojos.UserType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
            versionViewHolder.title.setText(list.get(i).getTitle());
            versionViewHolder.subTitle.setText(list.get(i).getvalue());
            if (!MkShop.Role.equalsIgnoreCase(UserType.ADMIN.name()) && versionViewHolder.title.getText().toString().equalsIgnoreCase("role"))
                versionViewHolder.imageView.setEnabled(false);
            else
                versionViewHolder.imageView.setEnabled(true);


            versionViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(context)
                            .title("edit " + versionViewHolder.title.getText())
                            .input(versionViewHolder.title.getText(), versionViewHolder.subTitle.getText(), new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, final CharSequence input) {

                                    Map map = new HashMap();
                                    map.put(versionViewHolder.title.getText(), input.toString());
                                    Client.INSTANCE.updateProfile(MkShop.AUTH, username, map, new Callback<String>() {
                                        @Override
                                        public void success(String s, Response response) {

                                            MkShop.toast(context, s);
                                            if (s != null && s.equalsIgnoreCase("profile updated"))
                                                versionViewHolder.subTitle.setText(input.toString());

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {

                                            if (error.getKind().equals(RetrofitError.Kind.NETWORK))
                                                MkShop.toast(context, "please check your internet connection");


                                        }
                                    });
                                }
                            }).show();
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
            title = (TextView) itemView.findViewById(R.id.title);
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
