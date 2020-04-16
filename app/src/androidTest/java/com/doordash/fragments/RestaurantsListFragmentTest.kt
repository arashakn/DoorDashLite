package com.doordash.fragments

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)

class RestaurantsListFragmentTest {

    @get : Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    val LIST_ITEM_IN_TEST = 0
    val LIST_ITEM_IN_TEST_TEXT = "A Good Morning Café"
    val LAST_ITEM_IN_TEST = 48


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
    fun tes_isRestaurant_ListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.rvRestaurants)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    /**
     * Select list item , navigate to detail fragment
     * Correct restaurant is in view?
     */
    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.rvRestaurants)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RestaurantsAdapter.RestaurantsViewHolder>(
                LIST_ITEM_IN_TEST,
                click()
            )
        )
        onView(withId(R.id.rvRestaurantTitle)).check(
            ViewAssertions.matches(
                withText(
                    LIST_ITEM_IN_TEST_TEXT
                )
            )
        )
    }

    /**
     * Scroll to an item in Recycle View
     */

    @Test
    fun test_Recycler_Scroll() {
        onView(withId(R.id.rvRestaurants)).perform(
            RecyclerViewActions.scrollToPosition<RestaurantsAdapter.RestaurantsViewHolder>(
                LAST_ITEM_IN_TEST
            )
        )
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
        onView(withId(R.id.rvRestaurantTitle)).check(
            ViewAssertions.matches(
                withText(
                    LIST_ITEM_IN_TEST_TEXT
                )
            )
        )

        Espresso.pressBack()

        // Confirm RestaurantListFragment in view
        onView(withId(R.id.rvRestaurants)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}