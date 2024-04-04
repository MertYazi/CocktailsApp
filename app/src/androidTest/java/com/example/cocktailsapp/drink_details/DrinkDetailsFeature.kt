package com.example.cocktailsapp.drink_details

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.example.cocktailsapp.BaseUITest
import com.example.cocktailsapp.R
import com.example.cocktailsapp.shared.presentation.CocktailsActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test


class DrinkDetailsFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysDrinkDetails() {
        navigateToDrinkDetailsFragment()

        onView(
            withId(R.id.rv_instructions_drink_details)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

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

    /*@Test
    fun displaysLoaderWhileFetchingDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(500)
        navigateToDrinkDetailsFragment()
        assertDisplayed(R.id.loader)
    }*/

    @Test
    fun hideLoader() {
        navigateToDrinkDetailsFragment()
        onView(
            withId(R.id.rv_instructions_drink_details)
        ).waitUntilVisible(5000)
        Thread.sleep(500)
        assertNotDisplayed(R.id.loader)
    }

    private fun navigateToDrinkDetailsFragment() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)

        onView(
            allOf(
                withId(R.id.iv_item_category),
                isDescendantOfA(nthChildOf(withId(R.id.rv_category_fragment), 0))
            )
        )
            .perform(click())

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .perform(click())
    }

}