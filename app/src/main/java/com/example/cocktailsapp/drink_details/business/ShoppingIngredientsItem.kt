package com.example.cocktailsapp.drink_details.business

data class ShoppingIngredientsItem(
    val drinkId: String,
    val drinkName: String,
    val ingredientName: String,
    val ingredientMeasure: String,
    val isAddedToShopping: Boolean = false
)
