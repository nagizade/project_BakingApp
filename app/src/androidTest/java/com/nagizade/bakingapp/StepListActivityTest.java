package com.nagizade.bakingapp;

/**
 * Created by Hasan Nagizade on 28.02.2019
 */

import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;

import com.nagizade.bakingapp.activity.MainActivity;
import com.nagizade.bakingapp.activity.NoInternetActivity;
import com.nagizade.bakingapp.activity.StepDetailActivity;
import com.nagizade.bakingapp.activity.StepListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepListActivityTest {

    // Opening MainActivity
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void ingredientsClick_OpensStepDetailActivity_orStepDetailFragment() {

        // Waiting until RecyclerView in MainActivity get's loaded with elements.
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());

            //Performing click on first element of RecyclerView
            onView(withId(R.id.rv_recipes)).perform(
                    RecyclerViewActions.actionOnItemAtPosition(0,click()));

            onView(withId(R.id.step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

            boolean isTablet = getApplicationContext().getResources().getBoolean(R.bool.isLarge);

            if(!isTablet) {
                //If device is not tablet checking if StepDetailActivity is opened
                intended(hasComponent(new ComponentName(getApplicationContext(), StepDetailActivity.class)));
            } else {
                // If device is tablet checking if StepDetailFragment layout is shown
                onView(withId(R.id.step_container)).check(matches(isDisplayed()));
            }
        }
}
