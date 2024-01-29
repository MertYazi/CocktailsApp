package com.example.cocktailsapp.home.presentation

sealed class HomeViewState {
    object Loading: HomeViewState()
    object Error: HomeViewState()
    data class ContentCategory(val categories: CategoryViewState): HomeViewState()
    data class ContentGlass(val glasses: GlassViewState): HomeViewState()
    data class ContentIngredient(val ingredients: IngredientViewState): HomeViewState()
    data class ContentAlcohol(val alcohols: AlcoholViewState): HomeViewState()
}
