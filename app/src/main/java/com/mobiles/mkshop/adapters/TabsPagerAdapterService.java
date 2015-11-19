package com.mobiles.mkshop.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobiles.mkshop.application.Myenum;
import com.mobiles.mkshop.fragments.ServiceReportList;
import com.mobiles.mkshop.pojos.Status;

/**
 * Created by vaibhav on 1/7/15.
 */
public class TabsPagerAdapterService extends FragmentStatePagerAdapter {


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    int size;

    public TabsPagerAdapterService(FragmentManager fm) {
        super(fm);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:


                size = Myenum.INSTANCE.getServiceList(Status.PENDING) != null ? Myenum.INSTANCE.getServiceList(Status.PENDING).size() : 0;
                title = Status.PENDING.name() + " (" + size + ")";
                break;
            case 1:

                size = Myenum.INSTANCE.getServiceList(Status.PROCESSING) != null ? Myenum.INSTANCE.getServiceList(Status.PROCESSING).size() : 0;
                title = Status.PROCESSING.name() + " (" + size + ")";
                break;
            case 2:
                size = Myenum.INSTANCE.getServiceList(Status.PNA) != null ? Myenum.INSTANCE.getServiceList(Status.PNA).size() : 0;
                title = Status.PNA.name() + " (" + size + ")";
                break;
            case 3:
                size = Myenum.INSTANCE.getServiceList(Status.DONE) != null ? Myenum.INSTANCE.getServiceList(Status.DONE).size() : 0;
                title = Status.DONE.name() + " (" + size + ")";
                break;
            case 4:
                size = Myenum.INSTANCE.getServiceList(Status.DELIVERED) != null ? Myenum.INSTANCE.getServiceList(Status.DELIVERED).size() : 0;
                title = Status.DELIVERED.name() + " (" + size + ")";
                break;
            case 5:
                size = Myenum.INSTANCE.getServiceList(Status.RETURN) != null ? Myenum.INSTANCE.getServiceList(Status.RETURN).size() : 0;
                title = Status.RETURN.name() + " (" + size + ")";
                break;
            case 6:
                size = Myenum.INSTANCE.getServiceList(Status.RETURNED) != null ? Myenum.INSTANCE.getServiceList(Status.RETURNED).size() : 0;
                title = Status.RETURNED.name() + " (" + size + ")";
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
                serviceReportList.setStatus(Status.PROCESSING);
                break;
            case 2:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.PNA);
                break;
            case 3:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.DONE);
                break;
            case 4:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.DELIVERED);
                break;
            case 5:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.RETURN);
                break;
            case 6:
                // Games fragment activity
                serviceReportList = new ServiceReportList();
                serviceReportList.setStatus(Status.RETURNED);
                break;

        }

        return serviceReportList;
    }

    @Override
    public int getCount() {
        return 7;
    }
}
