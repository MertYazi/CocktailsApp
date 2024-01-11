package com.example.cocktailsapp.favorites.presentation


sealed class FavoritesViewState {

    object Loading: FavoritesViewState()

    object Error: FavoritesViewState()

    data class ContentFavorites(val drinks: FavoriteDetailsViewState): FavoritesViewState()
}