package com.example.cocktailsapp.home.presentation

/**
 * Created by Mert on 2024
 */
sealed class HomeViewState {
    object Loading: HomeViewState()
    object Error: HomeViewState()
    data class ContentCategory(val categories: CategoryViewState): HomeViewState()
    data class ContentGlass(val glasses: GlassViewState): HomeViewState()
    data class ContentIngredient(val ingredients: IngredientViewState): HomeViewState()
    data class ContentAlcohol(val alcohols: AlcoholViewState): HomeViewState()
}
