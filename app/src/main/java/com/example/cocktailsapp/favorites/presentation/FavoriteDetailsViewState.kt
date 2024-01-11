package com.example.cocktailsapp.favorites.presentation

import com.example.cocktailsapp.drink_details.business.DrinkDetailsItem

data class FavoriteDetailsViewState(
    val drinks: List<DrinkDetailsItem>
)