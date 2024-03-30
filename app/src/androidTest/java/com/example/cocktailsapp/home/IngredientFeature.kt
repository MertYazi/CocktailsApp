package com.example.cocktailsapp.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.example.cocktailsapp.BaseUITest
import com.example.cocktailsapp.shared.presentation.CocktailsActivity
import com.example.cocktailsapp.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test

class IngredientFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfIngredients() {
        navigateToIngredientFragment()

        onView(
            withId(R.id.rv_ingredient_fragment)
        ).waitUntilVisible(5000)

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

    /*@Test
    fun displaysLoaderWhileFetchingIngredients() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(500)
        navigateToIngredientFragment()
        assertDisplayed(R.id.loader)
    }*/

    @Test
    fun hideLoader() {
        navigateToIngredientFragment()
        onView(
            withId(R.id.rv_ingredient_fragment)
        ).waitUntilVisible(5000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDrinkListScreen() {
        navigateToIngredientFragment()
        onView(
            withId(R.id.rv_ingredient_fragment)
        ).waitUntilVisible(5000)
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
    }

}