package com.example.cocktailsapp.search.presentation

/**
 * Created by Mert on 2024
 */

sealed class SearchViewState {

    object Loading: SearchViewState()

    object Error: SearchViewState()

    data class ContentSearch(val drinks: SearchDetailsViewState): SearchViewState()
}