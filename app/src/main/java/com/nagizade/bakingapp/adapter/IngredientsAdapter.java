package com.nagizade.bakingapp.adapter;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.model.Ingredient;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredients;

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIngredientText,tvIngredientMeasure,tvIngredientQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientText        = itemView.findViewById(R.id.tv_ingredientText);
            tvIngredientMeasure     = itemView.findViewById(R.id.tv_ingredientMeasure);
            tvIngredientQuantity    = itemView.findViewById(R.id.tv_ingredientQuantity);
        }
    }

    public IngredientsAdapter(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.ingredient_row,parent,false);
        return new IngredientsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
         Ingredient ingredient  = ingredients.get(position);
         String measure         = ingredient.getMeasure();
         String ingredient_text = ingredient.getIngredient();
         int quantity        =  ingredient.getQuantity().intValue();

         holder.tvIngredientText.setText(ingredient_text);
         holder.tvIngredientQuantity.setText(String.valueOf(quantity));
         holder.tvIngredientMeasure.setText(measure);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
