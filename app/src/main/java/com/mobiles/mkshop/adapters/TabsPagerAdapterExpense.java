package com.mobiles.mkshop.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.mobiles.mkshop.fragments.ViewExpenseReportList;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapterExpense extends FragmentStatePagerAdapter {


    public TabsPagerAdapterExpense(FragmentManager fm) {
        super(fm);
    }

    private final SparseArray<ViewExpenseReportList> mPageReferences = new SparseArray<ViewExpenseReportList>();


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Salary";
                break;
            case 1:
                title = "Incentive";
                break;

        }
        return title;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {


        ViewExpenseReportList myFragment = ViewExpenseReportList.newInstance(position);
        mPageReferences.put(position, myFragment);
        // return  new SalesReportList().newInstance(position);
        return myFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferences.remove(position);
    }


    public ViewExpenseReportList getFragment(int key) {
        return mPageReferences.get(key);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
