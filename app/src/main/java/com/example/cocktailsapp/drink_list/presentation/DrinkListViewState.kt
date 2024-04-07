package com.example.cocktailsapp.drink_list.presentation

/**
 * Created by Mert on 2024
 */
sealed class DrinkListViewState {
    object Loading: DrinkListViewState()
    object Error: DrinkListViewState()
    data class ContentDrinkByCategory(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByGlass(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByIngredient(val drinks: DrinkViewState): DrinkListViewState()
    data class ContentDrinkByAlcohol(val drinks: DrinkViewState): DrinkListViewState()
}
