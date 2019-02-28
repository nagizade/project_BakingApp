package com.nagizade.bakingapp.utils;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingService {
    private static BakingService mInstance;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private Retrofit mRetrofit;

    private BakingService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static BakingService getInstance() {
        if(mInstance == null) {
            mInstance = new BakingService();
        }
        return mInstance;
    }

    public BakingAPI getBakingAPI() {
        return mRetrofit.create(BakingAPI.class);
    }

}
