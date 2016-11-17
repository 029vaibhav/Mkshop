package com.mobiles.msm.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mobiles.msm.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vaibhav on 23/7/15.
 */
public class ViewPagerAdapter extends PagerAdapter {


    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> path;


    public ViewPagerAdapter(Context context, List<String> path) {
        this.path = path;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(path!=null)return path.size();
        else return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_view_product_image_view, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.mobileImage);


        String url = path.get(position).replace("\\", "");
        Picasso.with(mContext).load(url).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}

