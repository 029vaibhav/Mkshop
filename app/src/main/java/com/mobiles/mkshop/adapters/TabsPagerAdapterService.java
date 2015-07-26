package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobiles.mkshop.fragments.ServiceReportList;
import com.mobiles.mkshop.pojos.Status;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapterService extends FragmentStatePagerAdapter {


    public TabsPagerAdapterService(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = Status.PENDING.name();
                break;
            case 1:
                title = Status.PROCESSING.name();
                break;
            case 2:
                title = Status.PNA.name();
                break;
            case 3:
                title = Status.DONE.name();
                break;
            case 4:
                title = Status.DELIVERED.name();
                break;
            case 5:
                title = Status.RETURN.name();
                break;
            case 6:
                title = Status.RETURNED.name();
                break;

        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {

        ServiceReportList serviceReportList = null;
        switch (position) {
            case 0:
                // Top Rated fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.PENDING);
                break;
            case 1:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.PROCESSING);
                break;
            case 2:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.PNA);
                break;
            case 3:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.DONE);
                break;
            case 4:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.DELIVERED);
                break;
            case 5:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.RETURN);
                break;
            case 6:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus( Status.RETURNED);
                break;

        }

        return serviceReportList;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
