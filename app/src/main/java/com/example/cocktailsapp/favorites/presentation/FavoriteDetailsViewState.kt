package com.example.cocktailsapp.favorites.presentation

import com.example.cocktailsapp.shared.business.DrinkDetailsItem

data class FavoriteDetailsViewState(
    val drinks: ArrayList<DrinkDetailsItem>
)