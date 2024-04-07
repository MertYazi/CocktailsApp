package com.example.cocktailsapp.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mert on 2024
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateSearch = MutableStateFlow<SearchViewState>(SearchViewState.Loading)
    val viewStateSearch = _viewStateSearch.asStateFlow()

    fun searchDrinks(drink: String) = viewModelScope.launch(dispatcher) {
        repository.searchDrinks(drink).collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateSearch.value = SearchViewState.Error
                }
                is Result.Success -> {
                    val drinks = SearchDetailsViewState(
                        result.data.drinks
                    )
                    _viewStateSearch.value = SearchViewState.ContentSearch(
                        drinks
                    )
                }
            }
        }
    }
}