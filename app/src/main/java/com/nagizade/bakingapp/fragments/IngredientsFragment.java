package com.nagizade.bakingapp.fragments;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.activity.StepDetailActivity;
import com.nagizade.bakingapp.activity.StepListActivity;
import com.nagizade.bakingapp.adapter.IngredientsAdapter;
import com.nagizade.bakingapp.model.Ingredient;
import com.nagizade.bakingapp.model.Recipe;
import com.nagizade.bakingapp.model.Step;
import com.nagizade.bakingapp.widget.RecipeWriter;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    public static final String ARG_INGREDIENTS_ARRAY = "ingredients_array";
    private ArrayList<Ingredient>   ingredients;
    private Toolbar                 detailToolbar;
    private RecyclerView            rvIngredients;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_INGREDIENTS_ARRAY)) {

            ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS_ARRAY);

            Activity activity = this.getActivity();
            assert activity != null;
            detailToolbar   = (Toolbar) activity.findViewById(R.id.tb_details);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_ingredients, container, false);
        rvIngredients   =   rootView.findViewById(R.id.rv_ingredients);

        setupIngredients(ingredients);

        return rootView;
    }

    private void setupIngredients(ArrayList<Ingredient> ingredients) {
        if(ingredients != null) {

            // Setting Toolbar title
            if (detailToolbar != null) {
                detailToolbar.setTitle("Ingredients");
            }

            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rvIngredients.setLayoutManager(layoutManager);
            rvIngredients.setAdapter(ingredientsAdapter);

        }
    }
}
