package com.example.cocktailsapp.search.presentation


sealed class SearchViewState {

    object Loading: SearchViewState()

    object Error: SearchViewState()

    data class ContentSearch(val drinks: SearchDetailsViewState): SearchViewState()
}