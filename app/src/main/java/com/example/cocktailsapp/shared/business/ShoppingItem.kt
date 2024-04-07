package com.example.cocktailsapp.shared.business

/**
 * Created by Mert on 2024
 */
data class ShoppingItem(
    var drinkId: String = "",
    var drinkName: String = "",
    var drinkCategory: String = "",
    var drinkAlcoholic: String = "",
    var ingredientName: String = "",
    var ingredientMeasure: String = "",
    var ingredientPosition: String = "",
    var isAddedToShopping: Boolean = false
)
