package com.example.cocktailsapp.drink_list.presentation

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

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateDrinks = MutableStateFlow<DrinkListViewState>(DrinkListViewState.Loading)
    val viewStateDrinks = _viewStateDrinks.asStateFlow()

    fun getDrinksByCategory(category: String) = viewModelScope.launch(dispatcher) {
        repository.getDrinksByCategory(category).collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateDrinks.value = DrinkListViewState.Error
                }
                is Result.Success -> {
                    val drinks = DrinkViewState(
                        result.data.drinks
                    )
                    _viewStateDrinks.value = DrinkListViewState.ContentDrinkByCategory(
                        drinks
                    )
                }
            }
        }
    }

    fun getDrinksByGlass(glass: String) = viewModelScope.launch(dispatcher) {
        repository.getDrinksByGlass(glass).collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateDrinks.value = DrinkListViewState.Error
                }
                is Result.Success -> {
                    val drinks = DrinkViewState(
                        result.data.drinks
                    )
                    _viewStateDrinks.value = DrinkListViewState.ContentDrinkByGlass(
                        drinks
                    )
                }
            }
        }
    }

    fun getDrinksByIngredient(ingredient: String) = viewModelScope.launch(dispatcher) {
        repository.getDrinksByIngredient(ingredient).collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateDrinks.value = DrinkListViewState.Error
                }
                is Result.Success -> {
                    val drinks = DrinkViewState(
                        result.data.drinks
                    )
                    _viewStateDrinks.value = DrinkListViewState.ContentDrinkByIngredient(
                        drinks
                    )
                }
            }
        }
    }

    fun getDrinksByAlcohol(alcohol: String) = viewModelScope.launch(dispatcher) {
        repository.getDrinksByAlcohol(alcohol).collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateDrinks.value = DrinkListViewState.Error
                }
                is Result.Success -> {
                    val drinks = DrinkViewState(
                        result.data.drinks
                    )
                    _viewStateDrinks.value = DrinkListViewState.ContentDrinkByAlcohol(
                        drinks
                    )
                }
            }
        }
    }
}