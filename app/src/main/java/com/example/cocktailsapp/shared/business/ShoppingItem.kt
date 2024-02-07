package com.example.cocktailsapp.shared.business

data class ShoppingItem(
    var drinkId: String = "",
    var drinkName: String = "",
    val drinkCategory: String = "",
    val drinkAlcoholic: String = "",
    var ingredientName: String = "",
    val ingredientMeasure: String = "",
    val ingredientPosition: String = "",
    var isAddedToShopping: Boolean = false
)
