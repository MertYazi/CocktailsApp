package com.example.cocktailsapp.search

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
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
import org.junit.Rule
import org.junit.Test

class SearchFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfCocktailsComesFromCategory() {
        navigateToSearchFragment()

        onView(
            isAssignableFrom(EditText::class.java)
        )
            .check(matches(withText("margarita")))
            .check(matches(isDisplayed()))

        assertRecyclerViewItemCount(R.id.rv_search_fragment, 6)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_search_fragment), 0))
            )
        )
            .check(matches(withText("MARGARITA")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_search_fragment), 0))
            )
        )
            .check(matches(withText("Ordinary Drink")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_search_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_search_fragment)
        )
            .check(matches(withText("Search")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_search_fragment)
        )
            .check(matches(withText("6 Cocktails")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingCocktails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        navigateToSearchFragment()
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        navigateToSearchFragment()
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailsScreen() {
        navigateToSearchFragment()
        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_search_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_details)
    }

    private fun navigateToSearchFragment() {
        onView(withId(R.id.sv_search))
            .perform(click())
        onView(
            isAssignableFrom(EditText::class.java)
        )
            .perform(
                ViewActions.typeText("margarita"),
                ViewActions.pressKey(KeyEvent.KEYCODE_ENTER)
            )
        Thread.sleep(1500)
    }

}