package com.example.cocktailsapp.favorites.presentation

/**
 * Created by Mert on 2024
 */

sealed class FavoritesViewState {

    object Loading: FavoritesViewState()

    object Error: FavoritesViewState()

    data class ContentFavorites(val drinks: FavoriteDetailsViewState): FavoritesViewState()
}