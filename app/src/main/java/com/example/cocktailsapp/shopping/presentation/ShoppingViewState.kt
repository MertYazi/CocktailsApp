package com.example.cocktailsapp.shopping.presentation

/**
 * Created by Mert on 2024
 */
sealed class ShoppingViewState {

    object Loading: ShoppingViewState()

    object Error: ShoppingViewState()

    data class ContentShopping(val drinks: ShoppingDetailsViewState): ShoppingViewState()
}