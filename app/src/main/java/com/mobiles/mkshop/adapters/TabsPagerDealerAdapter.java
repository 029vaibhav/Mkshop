package com.mobiles.mkshop.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mobiles.mkshop.fragments.DealerReportViewPagerFragment;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerDealerAdapter extends FragmentStatePagerAdapter {

    String dealerName;

    public TabsPagerDealerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    private final SparseArray<DealerReportViewPagerFragment> mPageReferences = new SparseArray<DealerReportViewPagerFragment>();


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Purchase";
                break;
            case 1:
                title = "Payment";
                break;

        }
        return title;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {


        DealerReportViewPagerFragment myFragment = DealerReportViewPagerFragment.newInstance(position,dealerName);
        mPageReferences.put(position, myFragment);
        // return  new DealerReportViewPagerFragment().newInstance(position);
        return myFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferences.remove(position);
    }


    public DealerReportViewPagerFragment getFragment(int key) {
        return mPageReferences.get(key);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
