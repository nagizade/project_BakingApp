package com.nagizade.bakingapp.activity;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.adapter.RecipesAdapter;
import com.nagizade.bakingapp.model.Ingredient;
import com.nagizade.bakingapp.model.Recipe;
import com.nagizade.bakingapp.model.Step;
import com.nagizade.bakingapp.utils.BakingService;
import com.nagizade.bakingapp.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LABEL_RECIPE = "recipe";

    private RecyclerView rvRecipes;
    private ArrayList<Recipe> recipes;
    private  RecipesAdapter recipesAdapter;

    private CountingIdlingResource countingIdlingResource = new CountingIdlingResource("COUNTER_FOR_IDLE");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isOnline()) {
            Intent intent = new Intent(MainActivity.this,NoInternetActivity.class);
            startActivity(intent);
        } else {
            getRecipes();
        }
    }

    private void getRecipes() {
        boolean isTablet    = getResources().getBoolean(R.bool.isLarge);

        if(isTablet) {
            countingIdlingResource.increment();
            GridLayoutManager layoutManager = new GridLayoutManager(this,3);
            rvRecipes.setLayoutManager(layoutManager);
            BakingService.getInstance()
                    .getBakingAPI()
                    .getRecipes()
                    .enqueue(new Callback<List<Recipe>>() {
                        @Override
                        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                            if(response.body() != null ) {
                                recipes = new ArrayList<>();
                                recipes.addAll(response.body());
                            }
                            recipesAdapter = new RecipesAdapter(recipes,getItemClicker());
                            rvRecipes.setAdapter(recipesAdapter);
                            countingIdlingResource.decrement();
                        }

                        @Override
                        public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        }
                    });
        } else {
            countingIdlingResource.increment();
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvRecipes.setLayoutManager(layoutManager);
            BakingService.getInstance()
                    .getBakingAPI()
                    .getRecipes()
                    .enqueue(new Callback<List<Recipe>>() {
                        @Override
                        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                            if(response.body() != null ) {
                                recipes = new ArrayList<>();
                                recipes.addAll(response.body());
                            }
                            recipesAdapter = new RecipesAdapter(recipes,getItemClicker());
                            rvRecipes.setAdapter(recipesAdapter);
                            countingIdlingResource.decrement();
                        }

                        @Override
                        public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        }
                    });
        }
    }

    private void bindViews() {
        rvRecipes = findViewById(R.id.rv_recipes);
    }

    private ItemClickListener getItemClicker() {
        return new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ArrayList<Step> steps = new ArrayList<>(recipes.get(position).getSteps());
                ArrayList<Ingredient> ingredients = new ArrayList<>(recipes.get(position).getIngredients());
                Intent intent = new Intent(MainActivity.this,StepListActivity.class);
                intent.putExtra(LABEL_RECIPE,recipes.get(position));

                startActivity(intent);
            }
        };
    }

    public IdlingResource getIdlingResource() {
        return countingIdlingResource;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}


