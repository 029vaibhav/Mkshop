package com.mobiles.msm.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mobiles.msm.fragments.DealerReportViewPagerFragment;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerDealerAdapter extends FragmentStatePagerAdapter {

    Long dealerId;

    public TabsPagerDealerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
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


        DealerReportViewPagerFragment myFragment = DealerReportViewPagerFragment.newInstance(position, dealerId);
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
