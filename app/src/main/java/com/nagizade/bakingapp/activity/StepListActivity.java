package com.nagizade.bakingapp.activity;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.bakingapp.R;

import com.nagizade.bakingapp.adapter.StepListAdapter;
import com.nagizade.bakingapp.fragments.IngredientsFragment;
import com.nagizade.bakingapp.fragments.StepDetailFragment;
import com.nagizade.bakingapp.model.Ingredient;
import com.nagizade.bakingapp.model.Recipe;
import com.nagizade.bakingapp.model.Step;
import com.nagizade.bakingapp.utils.ItemClickListener;
import com.nagizade.bakingapp.widget.IngredientWidget;
import com.nagizade.bakingapp.widget.IngredientWidgetRemoteService;
import com.nagizade.bakingapp.widget.RecipeWriter;

import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private ArrayList<Step> steps;
    private ArrayList<Ingredient> ingredients;
    private Recipe  recipe;
    private TextView    tvIngredientsLabel;

    private static final String BUNDLE_KEY = "stepsList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.step_list);
        tvIngredientsLabel = findViewById(R.id.tv_ingredients_label);

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            recipe = intent.getParcelableExtra("recipe");
            steps = new ArrayList<>();
            ingredients = new ArrayList<>();
            steps.addAll(recipe.getSteps());
            ingredients.addAll(recipe.getIngredients());
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView,steps);
        } else {
            steps = savedInstanceState.getParcelableArrayList(BUNDLE_KEY);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView,steps);
        }

        tvIngredientsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTwoPane) {
                    StepListActivity activity = StepListActivity.this;
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList(IngredientsFragment.ARG_INGREDIENTS_ARRAY,ingredients);
                    IngredientsFragment fragment = new IngredientsFragment();
                    fragment.setArguments(arguments);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container,fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context,StepDetailActivity.class);
                    intent.putParcelableArrayListExtra(IngredientsFragment.ARG_INGREDIENTS_ARRAY,ingredients);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.ingredient_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_widget:
                RecipeWriter.writeRecipe(this,recipe);
                IngredientWidgetRemoteService.updateWidget(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_KEY,steps);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, ArrayList<Step> theSteps) {
        recyclerView.setAdapter(new StepListAdapter(this, theSteps, mTwoPane,getItemClickListener(this,theSteps)));
    }

    public ItemClickListener getItemClickListener(final StepListActivity mParentActivity,final ArrayList<Step> mValues) {
       return new ItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {
               if (mTwoPane) {
                   Bundle arguments = new Bundle();
                   arguments.putParcelable("step_object",mValues.get(position)); // sending step object to details fragment
                   arguments.putInt(StepDetailFragment.ARG_STEP_POS,position);
                   StepDetailFragment fragment = new StepDetailFragment();
                   fragment.setArguments(arguments);
                   mParentActivity.getSupportFragmentManager().beginTransaction()
                           .replace(R.id.step_detail_container, fragment)
                           .commit();
               } else {
                   Context context = view.getContext();
                   Intent intent = new Intent(context, StepDetailActivity.class);
                   intent.putParcelableArrayListExtra("steps_array",mValues);
                   intent.putExtra("step_position",position);
                   context.startActivity(intent);
               }
           }
       };
    }
}
