package com.example.cocktailsapp.home.presentation

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
class HomeViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateCategory = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewStateCategory = _viewStateCategory.asStateFlow()

    private val _viewStateGlass = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewStateGlass = _viewStateGlass.asStateFlow()

    private val _viewStateIngredient = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewStateIngredient = _viewStateIngredient.asStateFlow()

    private val _viewStateAlcohol = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewStateAlcohol = _viewStateAlcohol.asStateFlow()

    init {
        getCategories()
        getGlasses()
        getIngredients()
        getAlcohols()
    }

    private fun getCategories() = viewModelScope.launch(dispatcher) {
        repository.getCategories().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateCategory.value = HomeViewState.Error
                }
                is Result.Success -> {
                    val categories = CategoryViewState(
                        result.data.drinks
                    )
                    _viewStateCategory.value = HomeViewState.ContentCategory(
                        categories
                    )
                }
            }
        }
    }

    private fun getGlasses() = viewModelScope.launch(dispatcher) {
        repository.getGlasses().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateGlass.value = HomeViewState.Error
                }
                is Result.Success -> {
                    val glasses = GlassViewState(
                        result.data.drinks
                    )
                    _viewStateGlass.value = HomeViewState.ContentGlass(
                        glasses
                    )
                }
            }
        }
    }

    private fun getIngredients() = viewModelScope.launch(dispatcher) {
        repository.getIngredients().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateIngredient.value = HomeViewState.Error
                }
                is Result.Success -> {
                    val ingredients = IngredientViewState(
                        result.data.drinks
                    )
                    _viewStateIngredient.value = HomeViewState.ContentIngredient(
                        ingredients
                    )
                }
            }
        }
    }

    private fun getAlcohols() = viewModelScope.launch(dispatcher) {
        repository.getAlcohols().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateAlcohol.value = HomeViewState.Error
                }
                is Result.Success -> {
                    val alcohols = AlcoholViewState(
                        result.data.drinks
                    )
                    _viewStateAlcohol.value = HomeViewState.ContentAlcohol(
                        alcohols
                    )
                }
            }
        }
    }

}