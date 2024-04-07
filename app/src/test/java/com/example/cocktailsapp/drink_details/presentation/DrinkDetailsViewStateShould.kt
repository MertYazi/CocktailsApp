package com.example.cocktailsapp.drink_details.presentation

import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by Mert on 2024
 */
class DrinkDetailsViewStateShould {

    private lateinit var details: DrinkDetailsViewState.ContentDrinkDetails
    private val drinkDetailsItem = DrinkDetailsItem(
        "dateModified",
        "idDrink",
        "strAlcoholic",
        "strCategory",
        "strCreativeCommonsConfirmed",
        "strDrink",
        "strDrinkAlternate",
        "strDrinkThumb",
        "strGlass",
        "strIBA",
        "strImageAttribution",
        "strImageSource",
        " strIngredient1",
        "strIngredient10",
        "strIngredient11",
        "strIngredient12",
        "strIngredient13",
        "strIngredient14",
        "strIngredient15",
        "strIngredient2",
        "strIngredient3",
        "strIngredient4",
        "strIngredient5",
        "strIngredient6",
        "strIngredient7",
        "strIngredient8",
        "strIngredient9",
        "strInstructions",
        "strMeasure1",
        "strMeasure10",
        "strMeasure11",
        "strMeasure12",
        "strMeasure13",
        "strMeasure14",
        "strMeasure15",
        "strMeasure2",
        "strMeasure3",
        "strMeasure4",
        "strMeasure5",
        "strMeasure6",
        "strMeasure7",
        "strMeasure8",
        "strMeasure9",
        "strTags"
    )

    @Before
    fun setup() {
        details = DrinkDetailsViewState.ContentDrinkDetails(
            DetailsViewState(
                arrayListOf(
                    drinkDetailsItem
                )
            )
        )
    }

    @Test
    fun updateDetailsViewStateWhenFavoriteIconClicked() {
        val favoriteDrink = details.updateFavoriteDrink(
            DetailsViewState(
                arrayListOf(
                    drinkDetailsItem
                )
            ),
            true
        )
        assertTrue(favoriteDrink.drinks.drinks[0].isFavorite)
    }

    @Test
    fun updateDetailsViewStateWhenFirstShoppingIconClicked() {
        val shoppingDrink = details.updateShoppingItem(
            DetailsViewState(
                arrayListOf(
                    drinkDetailsItem
                )
            ),
            "1",
            true
        )
        assertTrue(shoppingDrink.drinks.drinks[0].isShopping1)
    }
}