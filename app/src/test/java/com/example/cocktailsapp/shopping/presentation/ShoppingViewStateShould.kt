package com.example.cocktailsapp.shopping.presentation

import com.example.cocktailsapp.shared.business.ShoppingItem
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ShoppingViewStateShould {

    private lateinit var shopping: ShoppingViewState.ContentShopping

    @Before
    fun setup() {
        shopping = ShoppingViewState.ContentShopping(
            ShoppingDetailsViewState(
                arrayListOf(
                    ShoppingItem(
                        "drinkId",
                        "drinkName",
                        "drinkCategory",
                        "drinkAlcoholic",
                        "ingredientName",
                        "ingredientMeasure"
                    )
                )
            )
        )
    }

    @Test
    fun updateShoppingDetailsViewStateWhenShoppingIconClicked() {
        val shoppingDrink = shopping.updateShoppingItem(
            ShoppingDetailsViewState(
                arrayListOf(
                    ShoppingItem(
                        "drinkId",
                        "drinkName",
                        "drinkCategory",
                        "drinkAlcoholic",
                        "ingredientName",
                        "ingredientMeasure"
                    )
                )
            ),
            ShoppingItem(
                "drinkId",
                "drinkName",
                "drinkCategory",
                "drinkAlcoholic",
                "ingredientName",
                "ingredientMeasure"
            )
        )

        assertEquals(shoppingDrink.drinks.drinks.size, 0)
    }

}