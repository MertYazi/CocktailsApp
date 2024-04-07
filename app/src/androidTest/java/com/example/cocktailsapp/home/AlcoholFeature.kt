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

/**
 * Created by Mert on 2024
 */
class AlcoholFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfAlcohol() {
        navigateToAlcoholFragment()

        onView(
            withId(R.id.rv_alcohol_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

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

    /*@Test
    fun displaysLoaderWhileFetchingAlcohols() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(500)
        navigateToAlcoholFragment()
        assertDisplayed(R.id.loader)
    }*/

    @Test
    fun hideLoader() {
        navigateToAlcoholFragment()
        onView(
            withId(R.id.rv_alcohol_fragment)
        ).waitUntilVisible(5000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDrinkListScreen() {
        navigateToAlcoholFragment()
        onView(
            withId(R.id.rv_alcohol_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)
        onView(
            allOf(
                withId(R.id.iv_item_alcohol),
                isDescendantOfA(nthChildOf(withId(R.id.rv_alcohol_fragment), 0))
            )
        )
            .perform(click())

        onView(
            withId(R.id.iv_drink_list_fragment)
        ).waitUntilVisible(5000)
        assertDisplayed(R.id.iv_drink_list_fragment)
    }

    private fun navigateToAlcoholFragment() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)
        for (i in 1..3) {
            onView(
                withId(R.id.viewpager)
            ).perform(swipeLeft())
        }
    }

}