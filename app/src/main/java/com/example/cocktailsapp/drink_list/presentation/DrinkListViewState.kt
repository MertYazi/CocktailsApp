package com.example.cocktailsapp.drink_list.presentation

import com.example.cocktailsapp.home.presentation.DrinkViewState

sealed class DrinkListViewState {
    object Loading: DrinkListViewState()
    object Error: DrinkListViewState()
    data class ContentDrinkByCategory(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByGlass(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByIngredient(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByAlcohol(val drinks: DrinkViewState): DrinkListViewState()
}
