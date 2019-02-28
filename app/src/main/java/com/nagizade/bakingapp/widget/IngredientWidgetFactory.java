package com.nagizade.bakingapp.widget;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.content.Context;

import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.model.Recipe;

public class IngredientWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe recipe;

    public IngredientWidgetFactory(Context applicationContext) {
        mContext = applicationContext;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
       recipe = RecipeWriter.getRecipe(mContext);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_row);

        row.setTextViewText(R.id.tv_widgetIngText, recipe.getIngredients().get(position).getIngredient());

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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
