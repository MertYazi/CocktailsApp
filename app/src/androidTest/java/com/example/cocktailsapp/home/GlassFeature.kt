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

class GlassFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfGlasses() {
        navigateToGlassFragment()
        assertRecyclerViewItemCount(R.id.rv_glass_fragment, 32)

        onView(
            allOf(
                withId(R.id.tv_item_glass),
                isDescendantOfA(nthChildOf(withId(R.id.rv_glass_fragment), 0))
            )
        )
            .check(matches(withText("Irish coffee cup")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_glass),
                isDescendantOfA(nthChildOf(withId(R.id.rv_glass_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingGlasses() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToGlassFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToGlassFragment()
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDrinkListScreen() {
        navigateToGlassFragment()
        onView(
            allOf(
                withId(R.id.iv_item_glass),
                isDescendantOfA(nthChildOf(withId(R.id.rv_glass_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_list_fragment)
    }

    private fun navigateToGlassFragment() {
        onView(
            withId(R.id.viewpager)
        ).perform(swipeLeft())
    }

}