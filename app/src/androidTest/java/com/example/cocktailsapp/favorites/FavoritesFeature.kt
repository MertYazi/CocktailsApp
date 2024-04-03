package com.example.cocktailsapp.favorites

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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

class FavoritesFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfFavoriteCocktails() {
        navigateToFavoritesFragment()

        onView(
            withId(R.id.rv_favorites_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

        assertRecyclerViewItemCount(R.id.rv_favorites_fragment, 9)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_favorites_fragment), 0))
            )
        )
            .check(matches(withText("3-MILE LONG ISLAND ICED TEA")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_favorites_fragment), 0))
            )
        )
            .check(matches(withText("Ordinary Drink")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_favorites_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_favorites_fragment)
        )
            .check(matches(withText("Favorites")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_favorites_fragment)
        )
            .check(matches(withText("9 Cocktails")))
            .check(matches(isDisplayed()))
    }

    /*@Test
    fun displaysLoaderWhileFetchingCocktails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(500)
        navigateToFavoritesFragment()
        assertDisplayed(R.id.loader)
    }*/

    @Test
    fun hideLoader() {
        navigateToFavoritesFragment()
        onView(
            withId(R.id.rv_favorites_fragment)
        ).waitUntilVisible(5000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailsScreen() {
        navigateToFavoritesFragment()
        onView(
            withId(R.id.rv_favorites_fragment)
        ).waitUntilVisible(5000)
        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_favorites_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_details)
    }

    private fun navigateToFavoritesFragment() {
        onView(
            withId(R.id.favoritesFragment)
        )
            .perform(click())
    }

}