package com.example.cocktailsapp.shopping

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
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
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class ShoppingFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfShoppingItems() {
        navigateToShoppingFragment()
        assertRecyclerViewItemCount(R.id.rv_shopping_fragment, 3)

        onView(
            allOf(
                withId(R.id.tv_item_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_shopping_fragment), 0))
            )
        )
            .check(matches(withText("Afterglow")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_shopping_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_count_item_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_shopping_fragment), 0))
            )
        )
            .check(matches(withText("1 item")))
            .check(matches(not(isDisplayed())))

        onView(
            withId(R.id.tv_shopping_fragment)
        )
            .check(matches(withText("Shopping List")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_shopping_fragment)
        )
            .check(matches(withText("6 items in 3 Cocktails")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingCocktails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToShoppingFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToShoppingFragment()
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun collapseCocktailsAndViewItemCount() {
        navigateToShoppingFragment()
        onView(
            allOf(
                withId(R.id.iv_item_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_shopping_fragment), 0))
            )
        )
            .perform(click())

        onView(
            allOf(
                withId(R.id.tv_count_item_shopping),
                isDescendantOfA(nthChildOf(withId(R.id.rv_shopping_fragment), 0))
            )
        )
            .check(matches(withText("1 item")))
            .check(matches(isDisplayed()))
    }

    private fun navigateToShoppingFragment() {
        onView(
            withId(R.id.shoppingFragment)
        )
            .perform(click())
        Thread.sleep(500)
    }

}