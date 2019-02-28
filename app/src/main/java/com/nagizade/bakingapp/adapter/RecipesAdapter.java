package com.nagizade.bakingapp.adapter;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nagizade.bakingapp.R;
import com.nagizade.bakingapp.utils.ItemClickListener;
import com.nagizade.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private ItemClickListener mListener;
    private ArrayList<Recipe> recipes;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemClickListener   itemClickListener;
        private TextView            tvRecipeName;
        private TextView            tvServings;

        ViewHolder(View itemView,ItemClickListener listener) {
            super(itemView);
            mListener = listener;
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvServings   = itemView.findViewById(R.id.tv_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

    public RecipesAdapter(ArrayList<Recipe> recipesList, ItemClickListener listener) {
        this.recipes = recipesList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_row,parent,false);
        return new RecipesAdapter.ViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvServings.setText(String.valueOf(recipe.getServings()));

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


}
