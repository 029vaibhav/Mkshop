package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mobiles.mkshop.fragments.LeaderBoardList;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapterLeader extends FragmentStatePagerAdapter {


    public TabsPagerAdapterLeader(FragmentManager fm) {
        super(fm);
    }
    private final SparseArray<LeaderBoardList> mPageReferences = new SparseArray<LeaderBoardList>();


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title="Sales";
                break;
            case 1:
                title="Service";
                break;

        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {


        LeaderBoardList myFragment = LeaderBoardList.newInstance(position);
        mPageReferences.put(position, myFragment);
       // return  new SalesReportList().newInstance(position);
        return myFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferences.remove(position);
    }


    public LeaderBoardList getFragment(int key) {
        return mPageReferences.get(key);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
