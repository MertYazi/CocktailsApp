package com.example.cocktailsapp.drink_details

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.cocktailsapp.BaseUITest
import com.example.cocktailsapp.R
import com.example.cocktailsapp.di.idlingResource
import com.example.cocktailsapp.shared.presentation.CocktailsActivity
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test


class DrinkDetailsFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysDrinkDetails() {
        navigateToDrinkDetailsFragment()
        assertRecyclerViewItemCount(R.id.rv_instructions_drink_details, 9)

        onView(
            allOf(
                withId(R.id.tv_item_sub_shopping_name),
                isDescendantOfA(nthChildOf(withId(R.id.rv_instructions_drink_details), 0))
            )
        )
            .check(matches(withText("Gin")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_sub_shopping_measure),
                isDescendantOfA(nthChildOf(withId(R.id.rv_instructions_drink_details), 0))
            )
        )
            .check(matches(withText("1/2 oz")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_sub_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_instructions_drink_details), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.iv_favorite_drink)
        )
            .check(matches(isDisplayed()))

    }

    @Test
    fun displaysLoaderWhileFetchingDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToDrinkDetailsFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToDrinkDetailsFragment()
        assertNotDisplayed(R.id.loader)
    }

    private fun navigateToDrinkDetailsFragment() {
        Thread.sleep(500)
        onView(
            allOf(
                withId(R.id.iv_item_category),
                isDescendantOfA(nthChildOf(withId(R.id.rv_category_fragment), 0))
            )
        )
            .perform(click())
        Thread.sleep(500)
        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .perform(click())
        Thread.sleep(2000)
    }

}