package com.caroline.android.udacitycapstoneproject.view.contentprovider2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by carolinestewart on 11/2/16.
 */
public class MovieWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        ComponentName thisWidget = new ComponentName(context,
                MovieWidgetProvider.class);

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        context.startService(WidgetService.createIntent(context, allWidgetIds));
    }
}




