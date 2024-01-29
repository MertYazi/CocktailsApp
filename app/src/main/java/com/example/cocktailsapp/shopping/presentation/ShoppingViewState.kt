package com.example.cocktailsapp.shopping.presentation

import com.example.cocktailsapp.shared.business.ShoppingItem


sealed class ShoppingViewState {

    object Loading: ShoppingViewState()

    object Error: ShoppingViewState()

    data class ContentShopping(val drinks: ShoppingDetailsViewState): ShoppingViewState()
}

fun ShoppingViewState.ContentShopping.updateShoppingItem(
    shopping: ShoppingDetailsViewState,
    shoppingItem: ShoppingItem
): ShoppingViewState.ContentShopping {
    return ShoppingViewState.ContentShopping(drinks =
    if (shopping.drinks.contains(shoppingItem)) {
        shopping.drinks.remove(shoppingItem)
        shopping
    } else {
        shopping
    }
    )
}