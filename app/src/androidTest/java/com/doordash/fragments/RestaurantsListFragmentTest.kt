package com.doordash.fragments

import androidx.core.content.MimeTypeFilter.matches
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.doordash.MainActivity
import com.doordash.R
import com.doordash.adapters.RestaurantsAdapter
import com.doordash.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)

class RestaurantsListFragmentTest {

    @get : Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
     val LIST_ITEM_IN_TEST = 2

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * RecycleView comes in to view
     */

    @Test
    fun tes_isRestaurant_ListFragmentVisible_onAppLaunch(){
        Espresso.onView(ViewMatchers.withId(R.id.rvRestaurants)).check(ViewAssertions.matches(
            ViewMatchers.isDisplayed()
        ))
    }

    /**
     * Select list item , navigate to detail fragment
     * Correct restaurant is in view?
     */
    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.rvRestaurants)).perform(RecyclerViewActions.actionOnItemAtPosition< RestaurantsAdapter.RestaurantsViewHolder>(LIST_ITEM_IN_TEST,click()))
        onView(withId(R.id.rvRestaurantTitle)).check(ViewAssertions.matches(withText("7-Eleven")))

    }
    /**
     * Select list item , navigate to detail fragment
     * press back?
     */
    @Test
    fun test_backNavigation_toMovieListFragment() {
        // Click list item #LIST_ITEM_IN_TEST
        onView(withId(R.id.rvRestaurants))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RestaurantsAdapter.RestaurantsViewHolder>(
                    LIST_ITEM_IN_TEST,
                    click()
                )
            )

        // Confirm nav to DetailFragment and display title
        onView(withId(R.id.rvRestaurantTitle)).check(ViewAssertions.matches(withText("7-Eleven")))

        Espresso.pressBack()

        // Confirm RestaurantListFragment in view
        onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}