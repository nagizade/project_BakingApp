package com.nagizade.bakingapp.utils;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import com.nagizade.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAPI {
    @GET("/topher/2017/May/59121517_baking/baking.json")
    public Call<List<Recipe>> getRecipes();
}
