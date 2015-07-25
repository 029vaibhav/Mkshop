package com.mobiles.mkshop.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mobiles.mkshop.Fragments.SalesReportList;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {


    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private final SparseArray<SalesReportList> mPageReferences = new SparseArray<SalesReportList>();


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title="Mobile";
                break;
            case 1:
                title="Accessory";
                break;

        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {


        SalesReportList myFragment = SalesReportList.newInstance(position);
        mPageReferences.put(position, myFragment);
       // return  new SalesReportList().newInstance(position);
        return myFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferences.remove(position);
    }


    public SalesReportList getFragment(int key) {
        return mPageReferences.get(key);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
