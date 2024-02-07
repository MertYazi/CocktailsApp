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

class AlcoholFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfAlcohol() {
        navigateToAlcoholFragment()
        assertRecyclerViewItemCount(R.id.rv_alcohol_fragment, 3)

        onView(
            allOf(
                withId(R.id.tv_item_alcohol),
                isDescendantOfA(nthChildOf(withId(R.id.rv_alcohol_fragment), 0))
            )
        )
            .check(matches(withText("Non alcoholic")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_alcohol),
                isDescendantOfA(nthChildOf(withId(R.id.rv_alcohol_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingAlcohols() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToAlcoholFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToAlcoholFragment()
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDrinkListScreen() {
        navigateToAlcoholFragment()
        onView(
            allOf(
                withId(R.id.iv_item_alcohol),
                isDescendantOfA(nthChildOf(withId(R.id.rv_alcohol_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_list_fragment)
    }

    private fun navigateToAlcoholFragment() {
        for (i in 1..3) {
            onView(
                withId(R.id.viewpager)
            ).perform(swipeLeft())
        }
    }

}