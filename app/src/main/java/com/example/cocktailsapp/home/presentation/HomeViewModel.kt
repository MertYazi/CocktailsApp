package com.example.cocktailsapp.home.presentation

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
class HomeViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateCategory = MutableLiveData<HomeViewState>()
    val viewStateCategory: LiveData<HomeViewState>
        get() = _viewStateCategory

    private val _viewStateGlass = MutableLiveData<HomeViewState>()
    val viewStateGlass: LiveData<HomeViewState>
        get() = _viewStateGlass

    private val _viewStateIngredient = MutableLiveData<HomeViewState>()
    val viewStateIngredient: LiveData<HomeViewState>
        get() = _viewStateIngredient

    private val _viewStateAlcohol = MutableLiveData<HomeViewState>()
    val viewStateAlcohol: LiveData<HomeViewState>
        get() = _viewStateAlcohol

    init {
        getCategories()
        getGlasses()
        getIngredients()
        getAlcohols()
    }

    private fun getCategories() = viewModelScope.launch(dispatcher) {
        _viewStateCategory.postValue(HomeViewState.Loading)
        when (val result = repository.getCategories()) {
            is Result.Error -> {
                _viewStateCategory.postValue(HomeViewState.Error)
            }
            is Result.Success -> {
                val categories = CategoryViewState(
                    result.data.drinks
                )
                _viewStateCategory.postValue(
                    HomeViewState.ContentCategory(
                        categories
                    )
                )
            }
        }
    }

    private fun getGlasses() = viewModelScope.launch(dispatcher) {
        _viewStateGlass.postValue(HomeViewState.Loading)
        when (val result = repository.getGlasses()) {
            is Result.Error -> {
                _viewStateGlass.postValue(HomeViewState.Error)
            }
            is Result.Success -> {
                val glasses = GlassViewState(
                    result.data.drinks
                )
                _viewStateGlass.postValue(
                    HomeViewState.ContentGlass(
                        glasses
                    )
                )
            }
        }
    }

    private fun getIngredients() = viewModelScope.launch(dispatcher) {
        _viewStateIngredient.postValue(HomeViewState.Loading)
        when (val result = repository.getIngredients()) {
            is Result.Error -> {
                _viewStateIngredient.postValue(HomeViewState.Error)
            }
            is Result.Success -> {
                val ingredients = IngredientViewState(
                    result.data.drinks
                )
                _viewStateIngredient.postValue(
                    HomeViewState.ContentIngredient(
                        ingredients
                    )
                )
            }
        }
    }

    private fun getAlcohols() = viewModelScope.launch(dispatcher) {
        _viewStateAlcohol.postValue(HomeViewState.Loading)
        when (val result = repository.getAlcohols()) {
            is Result.Error -> {
                _viewStateAlcohol.postValue(HomeViewState.Error)
            }
            is Result.Success -> {
                val alcohols = AlcoholViewState(
                    result.data.drinks
                )
                _viewStateAlcohol.postValue(
                    HomeViewState.ContentAlcohol(
                        alcohols
                    )
                )
            }
        }
    }

}