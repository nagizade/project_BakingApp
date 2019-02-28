package com.nagizade.bakingapp.widget;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.model.Ingredient;
import com.nagizade.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
       /* CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/

       Recipe recipe = RecipeWriter.getRecipe(context);

       if(recipe != null) {
           // Construct the RemoteViews object
           RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
           views.setTextViewText(R.id.tv_ingredientsWidgetRecipeName, recipe.getName());

           Intent intent = new Intent(context, IngredientWidgetRemoteService.class);
           intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
           // Bind the remote adapter
           views.setRemoteAdapter(R.id.lv_ingredientsList, intent);

           appWidgetManager.updateAppWidget(appWidgetId,views);
           appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ingredientsList);

       }


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

