package com.nagizade.bakingapp.activity;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.fragments.IngredientsFragment;
import com.nagizade.bakingapp.fragments.StepDetailFragment;
import com.nagizade.bakingapp.model.Ingredient;
import com.nagizade.bakingapp.model.Step;

import java.util.ArrayList;

import static android.view.View.GONE;

public class StepDetailActivity extends AppCompatActivity {

    private Button btnPrev,btnNext;
    private int stepPosition;
    private LinearLayout navContainer;
    private ArrayList<Step> stepsArray;
    private ArrayList<Ingredient> ingredientsArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        btnNext = findViewById(R.id.btn_next);
        btnPrev = findViewById(R.id.btn_prev);
        navContainer = findViewById(R.id.step_nav_container);

        boolean isTablet    = getResources().getBoolean(R.bool.isLarge);

        // if user is in tablet we must hide navigation buttons
        if(isTablet) navContainer.setVisibility(GONE);

        // Show the Up button in the action bar.
        Toolbar detailsToolbar = findViewById(R.id.tb_details);
        setSupportActionBar(detailsToolbar);
        if (detailsToolbar != null) {
            setSupportActionBar(detailsToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {
            ingredientsArray = getIntent().getParcelableArrayListExtra(IngredientsFragment.ARG_INGREDIENTS_ARRAY);
            if(ingredientsArray != null) {
                createFragment(0,"ingredient");
            } else {
                stepsArray   = getIntent().getParcelableArrayListExtra("steps_array");
                stepPosition = getIntent().getIntExtra("step_position",0);
                createFragment(stepPosition,"step");
            }
        } else {
            stepsArray = savedInstanceState.getParcelableArrayList(StepDetailFragment.ARG_STEP_ARRAY);
            stepPosition = savedInstanceState.getInt(StepDetailFragment.ARG_STEP_POS);
            createFragment(stepPosition,"step");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = stepPosition + 1;
                createFragment(stepPosition,"step");
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepPosition = stepPosition -1;
                createFragment(stepPosition,"step");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(StepDetailFragment.ARG_STEP_ARRAY,stepsArray);
        outState.putInt(StepDetailFragment.ARG_STEP_POS,stepPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createFragment(int stepPosition,String fragmentType) {

        switch (fragmentType) {
            case "ingredient":
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                Bundle ingredientArguments = new Bundle();
                hideNavigation();
                ingredientArguments.putParcelableArrayList(IngredientsFragment.ARG_INGREDIENTS_ARRAY,ingredientsArray);
                ingredientsFragment.setArguments(ingredientArguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container,ingredientsFragment)
                        .commit();
                break;
            case "step":
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                Bundle stepDetailArguments = new Bundle();
                Step step = stepsArray.get(stepPosition);
                configureNavigation(stepPosition, stepsArray.size());
                stepDetailArguments.putParcelable(StepDetailFragment.ARG_STEP_OBJ,step);
                stepDetailArguments.putInt(StepDetailFragment.ARG_STEP_POS,stepPosition);
                stepDetailFragment.setArguments(stepDetailArguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailFragment)
                        .commit();
                break;
        }
    }

    /**
     * Method to configure navigation buttons. If we are in first element btnPrev, if in last element
     * btnNext must be disabled. We need two parameters.
     * @param stepPosition - current position in steps array.
     * @param stepsSize - size of steps array.
     */

    private void configureNavigation(int stepPosition,int stepsSize) {
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if(stepPosition == 0) {
            btnPrev.setEnabled(false);
        } else if(stepPosition == stepsSize-1) btnNext.setEnabled(false);
    }

    private void hideNavigation() {
        btnPrev.setVisibility(GONE);
        btnNext.setVisibility(GONE);
    }
}
