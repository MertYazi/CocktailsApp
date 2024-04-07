package com.example.cocktailsapp.favorites.presentation

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
class FavoritesViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateFavorites = MutableStateFlow<FavoritesViewState>(FavoritesViewState.Loading)
    val viewStateFavorites = _viewStateFavorites.asStateFlow()

    fun getFavorites() = viewModelScope.launch(dispatcher) {
        repository.getFavorites().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateFavorites.value = FavoritesViewState.Error
                }
                is Result.Success -> {
                    val drinks = FavoriteDetailsViewState(
                        result.data.drinks
                    )
                    _viewStateFavorites.value = FavoritesViewState.ContentFavorites(
                        drinks
                    )
                }
            }
        }
    }
}