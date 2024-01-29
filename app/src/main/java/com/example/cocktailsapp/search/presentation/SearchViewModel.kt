package com.example.cocktailsapp.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateSearch = MutableLiveData<SearchViewState>()
    val viewStateSearch: LiveData<SearchViewState>
        get() = _viewStateSearch

    fun searchDrinks(drink: String) = viewModelScope.launch(dispatcher) {
        _viewStateSearch.postValue(SearchViewState.Loading)
        when (val result = repository.searchDrinks(drink)) {
            is Result.Error -> {
                _viewStateSearch.postValue(SearchViewState.Error)
            }
            is Result.Success -> {
                val drinks = SearchDetailsViewState(
                    result.data.drinks
                )
                _viewStateSearch.postValue(
                    SearchViewState.ContentSearch(
                        drinks
                    )
                )
            }
        }
    }
}