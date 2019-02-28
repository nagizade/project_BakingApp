package com.nagizade.bakingapp.widget;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nagizade.bakingapp.model.Recipe;

import static android.content.Context.MODE_PRIVATE;

public class RecipeWriter {

    private static final String PREF_NAME = "BakingApp_WidgetRecipe";
    private static final String PREF_RECIPE = "Pref_Recipe";


    public static Recipe getRecipe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String value = sharedPreferences.getString(PREF_RECIPE,"");
        Gson gson = new Gson();
        return gson.fromJson(value,Recipe.class);
    }

    public static void writeRecipe(Context context,Recipe recipe) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);

        // Writing data to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_RECIPE,recipeString);
        editor.apply();
    }
 }
