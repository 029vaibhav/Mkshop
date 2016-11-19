package com.mobiles.msm.wigdet;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by vaibhav on 19/11/16.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        RemoteViewsFactory listProvidder = new CallsListRemoteViewsFactory(this.getApplicationContext(), intent);
        return listProvidder;
    }
}
