package com.nagizade.bakingapp.widget;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public class IngredientWidgetRemoteService extends RemoteViewsService {
    public IngredientWidgetRemoteService() {
        super();
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidget.class));


        IngredientWidget.updateWidgets(context,appWidgetManager,appWidgetIds);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientWidgetFactory(this.getApplicationContext());
    }
}
