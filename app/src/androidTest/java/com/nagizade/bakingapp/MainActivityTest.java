package com.nagizade.bakingapp;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import com.nagizade.bakingapp.activity.MainActivity;
import com.nagizade.bakingapp.activity.NoInternetActivity;
import com.nagizade.bakingapp.activity.StepListActivity;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Opening MainActivity
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    // Performing a click and opening StepListActivity
    @Test
    public void recipeClick_OpensStepListActivity() {

        // Waiting until RecyclerView in MainActivity get's loaded with elements.
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());

        if(isOnline()) {
            //Performing click on first element of RecyclerView
            onView(withId(R.id.rv_recipes)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0,click()));

            //Checking if StepListActivity is opened
            intended(hasComponent(new ComponentName(getApplicationContext(), StepListActivity.class)));
        } else {
            //Checking if NoInternetActivity is opened
            intended(hasComponent(new ComponentName(getApplicationContext(), NoInternetActivity.class)));
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
