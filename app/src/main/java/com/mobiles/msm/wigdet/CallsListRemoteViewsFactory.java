package com.mobiles.msm.wigdet;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mobiles.msm.R;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.Sales;

import org.joda.time.DateTime;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by vaibhav on 19/11/16.
 */
public class CallsListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private boolean dataIsValid;
    private int rowIdColumn;
    private DataSetObserver mDataSetObserver;
    Context context;
    List<Sales> body;
    int mobileSales = 0, accessorySale = 0;
    ;


    public CallsListRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
        executeQuery();

    }

    private void executeQuery() {
        DateTime dt = DateTime.now();
        String date = dt.toString("yyyy-MM-dd");
        Client.INSTANCE.getSalesReport(MyApplication.AUTH, date, date).enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {

                body = response.body();
                for (Sales sales : body) {
                    if (sales.getProductType() == ProductType.Mobile) {
                        mobileSales = mobileSales + Integer.parseInt(sales.getPrice());
                    }
                    if (sales.getProductType() == ProductType.Accessory) {
                        accessorySale = accessorySale + Integer.parseInt(sales.getPrice());
                    }
                }
                notifyAll();

            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {


            }
        });

    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        notify();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (body != null && body.size() > 0) {
            return 1;
        }
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        row.setTextViewText(R.id.mobile, context.getString(R.string.rs) + mobileSales);
        row.setTextViewText(R.id.accessory, context.getString(R.string.rs) + accessorySale);
        Intent fillInIntent = new Intent();
        row.setOnClickFillInIntent(R.id.root_view, fillInIntent);
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            synchronized (context) {
                super.onChanged();
                dataIsValid = true;
                context.notify();
            }
        }

        @Override
        public void onInvalidated() {
            synchronized (context) {
                super.onInvalidated();
                dataIsValid = false;
                context.notify();
            }
        }
    }


}
