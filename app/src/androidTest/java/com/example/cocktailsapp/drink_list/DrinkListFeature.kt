package com.example.cocktailsapp.drink_list

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
class DrinkListFeature: BaseUITest() {

    val mActivityRule = ActivityTestRule(CocktailsActivity::class.java)
        @Rule get

    @Test
    fun displaysListOfCocktailsComesFromCategory() {
        navigateToDrinkListFragmentFromCategory()
        checkDrinkRelatedFields(
            "3-MILE LONG ISLAND ICED TEA",
            "Ordinary Drink",
            "100 Cocktails")
    }

    @Test
    fun displaysListOfCocktailsComesFromGlass() {
        navigateToDrinkListFragmentFromGlass()
        checkDrinkRelatedFields(
            "A.D.M. (AFTER DINNER MINT)",
            "Irish coffee cup",
            "7 Cocktails")
    }

    @Test
    fun displaysListOfCocktailsComesFromIngredient() {
        navigateToDrinkListFragmentFromIngredient()
        checkDrinkRelatedFields(
            "151 FLORIDA BUSHWACKER",
            "Light rum",
            "44 Cocktails")
    }

    @Test
    fun displaysListOfCocktailsComesFromAlcohol() {
        navigateToDrinkListFragmentFromAlcohol()
        checkDrinkRelatedFields(
            "AFTERGLOW",
            "Non alcoholic",
            "58 Cocktails")
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
        Thread.sleep(1000)

        onView(
            allOf(
                withId(R.id.iv_item_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .perform(click())

        onView(
            withId(R.id.iv_drink_details)
        ).waitUntilVisible(5000)
        assertDisplayed(R.id.iv_drink_details)
    }

    private fun checkDrinkRelatedFields(name: String, filter: String, count: String) {
        onView(
            withId(R.id.rv_drink_list_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

        when (count) {
            "58 Cocktails" -> {
                assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 58)
            }
            "44 Cocktails" -> {
                assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 44)
            }
            "7 Cocktails" -> {
                assertRecyclerViewItemCount(R.id.rv_drink_list_fragment, 7)
            }
        }

        onView(
            allOf(
                withId(R.id.tv_item_name_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText(name)))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.tv_item_filter_drink_list),
                isDescendantOfA(nthChildOf(withId(R.id.rv_drink_list_fragment), 0))
            )
        )
            .check(matches(withText(filter)))
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
            .check(matches(withText(filter)))
            .check(matches(isDisplayed()))

        onView(
            withId(R.id.tv_count_drink_list_fragment)
        )
            .check(matches(withText(count)))
            .check(matches(isDisplayed()))
    }

    private fun navigateToDrinkListFragmentFromCategory() {
        onView(
            withId(R.id.rv_category_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

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
            withId(R.id.rv_glass_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

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
            withId(R.id.rv_ingredient_fragment)
        ).waitUntilVisible(5000)
        Thread.sleep(1000)

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
    }

}