package com.example.cocktailsapp.drink_list.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.CocktailsRepository
import com.example.cocktailsapp.Result
import com.example.cocktailsapp.home.presentation.DrinkViewState
import com.example.cocktailsapp.home.presentation.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkListViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateDrinks = MutableLiveData<DrinkListViewState>()
    val viewStateDrinks: LiveData<DrinkListViewState>
        get() = _viewStateDrinks

    fun getDrinksByCategory(category: String) = viewModelScope.launch(dispatcher) {
        _viewStateDrinks.postValue(DrinkListViewState.Loading)
        when (val result = repository.getDrinksByCategory(category)) {
            is Result.Error -> {
                _viewStateDrinks.postValue(DrinkListViewState.Error)
            }
            is Result.Success -> {
                val drinks = DrinkViewState(
                    result.data.drinks
                )
                _viewStateDrinks.postValue(
                    DrinkListViewState.ContentDrinkByCategory(
                        drinks
                    )
                )
            }
        }
    }

    fun getDrinksByGlass(glass: String) = viewModelScope.launch(dispatcher) {
        _viewStateDrinks.postValue(DrinkListViewState.Loading)
        when (val result = repository.getDrinksByGlass(glass)) {
            is Result.Error -> {
                _viewStateDrinks.postValue(DrinkListViewState.Error)
            }
            is Result.Success -> {
                val drinks = DrinkViewState(
                    result.data.drinks
                )
                _viewStateDrinks.postValue(
                    DrinkListViewState.ContentDrinkByGlass(
                        drinks
                    )
                )
            }
        }
    }

    fun getDrinksByIngredient(ingredient: String) = viewModelScope.launch(dispatcher) {
        _viewStateDrinks.postValue(DrinkListViewState.Loading)
        when (val result = repository.getDrinksByIngredient(ingredient)) {
            is Result.Error -> {
                _viewStateDrinks.postValue(DrinkListViewState.Error)
            }
            is Result.Success -> {
                val drinks = DrinkViewState(
                    result.data.drinks
                )
                _viewStateDrinks.postValue(
                    DrinkListViewState.ContentDrinkByIngredient(
                        drinks
                    )
                )
            }
        }
    }

    fun getDrinksByAlcohol(alcohol: String) = viewModelScope.launch(dispatcher) {
        _viewStateDrinks.postValue(DrinkListViewState.Loading)
        when (val result = repository.getDrinksByAlcohol(alcohol)) {
            is Result.Error -> {
                _viewStateDrinks.postValue(DrinkListViewState.Error)
            }
            is Result.Success -> {
                val drinks = DrinkViewState(
                    result.data.drinks
                )
                _viewStateDrinks.postValue(
                    DrinkListViewState.ContentDrinkByAlcohol(
                        drinks
                    )
                )
            }
        }
    }
}