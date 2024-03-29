package com.example.cocktailsapp.shopping.presentation

sealed class ShoppingViewState {

    object Loading: ShoppingViewState()

    object Error: ShoppingViewState()

    data class ContentShopping(val drinks: ShoppingDetailsViewState): ShoppingViewState()
}