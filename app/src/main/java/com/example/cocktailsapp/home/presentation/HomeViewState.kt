package com.example.cocktailsapp.home.presentation

sealed class HomeViewState {
    object Loading: HomeViewState()
    object Error: HomeViewState()
    data class ContentCategory(val categories: CategoryViewState): HomeViewState()
    data class ContentGlass(val glasses: GlassViewState): HomeViewState()
    data class ContentIngredient(val ingredients: IngredientViewState): HomeViewState()
    data class ContentAlcohol(val alcohols: AlcoholViewState): HomeViewState()
    data class ContentDrinkByCategory(val drinks: DrinkViewState): HomeViewState()
    data class ContentDrinkByGlass(val drinks: DrinkViewState): HomeViewState()
    data class ContentDrinkByAlcohol(val drinks: DrinkViewState): HomeViewState()
}
