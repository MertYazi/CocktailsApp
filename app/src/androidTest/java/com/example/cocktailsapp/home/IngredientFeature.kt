package com.example.cocktailsapp.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.cocktailsapp.BaseUITest
import com.example.cocktailsapp.shared.presentation.CocktailsActivity
import com.example.cocktailsapp.R
import com.example.cocktailsapp.di.idlingResource
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

class IngredientFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfIngredients() {
        navigateToIngredientFragment()
        assertRecyclerViewItemCount(R.id.rv_ingredient_fragment, 100)

        onView(
            allOf(
                withId(R.id.tv_item_ingredient),
                isDescendantOfA(nthChildOf(withId(R.id.rv_ingredient_fragment), 0))
            )
        )
            .check(matches(withText("Light rum")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_ingredient),
                isDescendantOfA(nthChildOf(withId(R.id.rv_ingredient_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingIngredients() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToIngredientFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToIngredientFragment()
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDrinkListScreen() {
        navigateToIngredientFragment()
        onView(
            allOf(
                withId(R.id.iv_item_ingredient),
                isDescendantOfA(nthChildOf(withId(R.id.rv_ingredient_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_list_fragment)
    }

    private fun navigateToIngredientFragment() {
        for (i in 1..2) {
            onView(
                withId(R.id.viewpager)
            ).perform(swipeLeft())
        }
        /* To much item to fetch and server does not have pagination.
         * So we'll have to wait a bit data to load. */
        Thread.sleep(500)
    }

}