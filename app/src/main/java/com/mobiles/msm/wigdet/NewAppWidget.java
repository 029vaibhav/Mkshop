package com.mobiles.msm.wigdet;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mobiles.msm.R;
import com.mobiles.msm.activities.LoginActivity;
import com.mobiles.msm.application.Client;
import com.mobiles.msm.application.MyApplication;
import com.mobiles.msm.pojos.enums.ProductType;
import com.mobiles.msm.pojos.models.Sales;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {


    int mobileSales = 0, accessorySale = 0;
    private List<Sales> body;
    Context context;
    int[] appWidgetIds;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.mobile, context.getString(R.string.rs) + "  " + mobileSales);
        views.setTextViewText(R.id.accessory, context.getString(R.string.rs) + "  " + accessorySale);

        views.setOnClickPendingIntent(R.id.mobile, pending);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        this.context = context;
        this.appWidgetIds = appWidgetIds;
        executorserviceInit();


        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Callable<Void> voidCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return executeQuery();
            }
        };
        Future<Void> submit = executorService.submit(voidCallable);
        try {
            Void aVoid = submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void executorserviceInit() {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(1);
        ScheduledFuture scheduledFuture =
                scheduledExecutorService.schedule(new Callable() {
                                                      public Object call() throws Exception {
                                                          schedule();
                                                          return "Called!";
                                                      }
                                                  },
                        1,
                        TimeUnit.HOURS);
        scheduledExecutorService.shutdown();
    }

    private void schedule() {

        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        context.sendBroadcast(intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private Void executeQuery() throws IOException {
        DateTime dt = DateTime.now().minusDays(1);
        String date = dt.toString("yyyy-MM-dd");
        Response<List<Sales>> execute = Client.INSTANCE.getSalesReport(MyApplication.AUTH, date, date).execute();

        body = execute.body();
        {

            for (Sales sales : body) {
                if (sales.getProductType() == ProductType.Mobile) {
                    mobileSales = mobileSales + Integer.parseInt(sales.getPrice());
                }
                if (sales.getProductType() == ProductType.Accessory) {
                    accessorySale = accessorySale + Integer.parseInt(sales.getPrice());
                }
            }
        }

        return null;

    }
}

