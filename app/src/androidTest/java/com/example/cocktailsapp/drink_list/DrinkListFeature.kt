package com.example.cocktailsapp.drink_list

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

class DrinkListFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfCocktailsComesFromCategory() {
        navigateToDrinkListFragmentFromCategory()

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)

        assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 100)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("3-MILE LONG ISLAND ICED TEA")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("Ordinary Drink")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_drink_list_fragment)
        )
            .check(matches(withText("Ordinary Drink")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_drink_list_fragment)
        )
            .check(matches(withText("100 Cocktails")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysListOfCocktailsComesFromGlass() {
        navigateToDrinkListFragmentFromGlass()

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)

        assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 7)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("A.D.M. (AFTER DINNER MINT)")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("Irish coffee cup")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_drink_list_fragment)
        )
            .check(matches(withText("Irish coffee cup")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_drink_list_fragment)
        )
            .check(matches(withText("7 Cocktails")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysListOfCocktailsComesFromIngredient() {
        navigateToDrinkListFragmentFromIngredient()

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)

        assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 44)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("151 FLORIDA BUSHWACKER")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("Light rum")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_drink_list_fragment)
        )
            .check(matches(withText("Light rum")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_drink_list_fragment)
        )
            .check(matches(withText("44 Cocktails")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysListOfCocktailsComesFromAlcohol() {
        navigateToDrinkListFragmentFromAlcohol()

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)

        assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 58)

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("AFTERGLOW")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText("Non alcoholic")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_drink_list_fragment)
        )
            .check(matches(withText("Non alcoholic")))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_drink_list_fragment)
        )
            .check(matches(withText("58 Cocktails")))
            .check(matches(isDisplayed()))
    }

    /*@Test
    fun displaysLoaderWhileFetchingCocktails() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        Thread.sleep(500)
        navigateToDrinkListFragmentFromCategory()
        assertDisplayed(R.id.loader)
    }*/

    @Test
    fun hideLoader() {
        navigateToDrinkListFragmentFromCategory()
        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailsScreen() {
        navigateToDrinkListFragmentFromCategory()

        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.iv_drink_details)
    }

    private fun navigateToDrinkListFragmentFromCategory() {
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
    }

    private fun navigateToDrinkListFragmentFromGlass() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)

        onView(
            withId(R.id.viewpager)
        ).perform(swipeLeft())

        onView(
            allOf(
                withId(R.id.iv_item_glass),
                isDescendantOfA(nthChildOf(withId(R.id.rv_glass_fragment), 0))
            )
        )
            .perform(click())
    }

    private fun navigateToDrinkListFragmentFromIngredient() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)

        for (i in 1..2) {
            onView(
                withId(R.id.viewpager)
            ).perform(swipeLeft())
        }

        onView(
            allOf(
                withId(R.id.iv_item_ingredient),
                isDescendantOfA(nthChildOf(withId(R.id.rv_ingredient_fragment), 0))
            )
        )
            .perform(click())
    }

    private fun navigateToDrinkListFragmentFromAlcohol() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)

        for (i in 1..3) {
            onView(
                withId(R.id.viewpager)
            ).perform(swipeLeft())
        }

        onView(
            allOf(
                withId(R.id.iv_item_alcohol),
                isDescendantOfA(nthChildOf(withId(R.id.rv_alcohol_fragment), 0))
            )
        )
            .perform(click())
    }

}