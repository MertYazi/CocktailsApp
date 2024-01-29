package com.example.cocktailsapp.shared.business

data class ShoppingItem(
    val drinkId: String = "",
    val drinkName: String = "",
    val drinkCategory: String = "",
    val drinkAlcoholic: String = "",
    val ingredientName: String = "",
    val ingredientMeasure: String = "",
    val ingredientPosition: String = "",
    var isAddedToShopping: Boolean = false
)
