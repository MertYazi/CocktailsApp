package com.example.cocktailsapp.favorites.presentation

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
class FavoritesViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateFavorites = MutableLiveData<FavoritesViewState>()
    val viewStateFavorites: LiveData<FavoritesViewState>
        get() = _viewStateFavorites

    fun getFavorites() = viewModelScope.launch(dispatcher) {
        _viewStateFavorites.postValue(FavoritesViewState.Loading)
        when (val result = repository.getFavorites()) {
            is Result.Error -> {
                _viewStateFavorites.postValue(FavoritesViewState.Error)
            }
            is Result.Success -> {
                val drinks = FavoriteDetailsViewState(
                    result.data.drinks
                )
                _viewStateFavorites.postValue(
                    FavoritesViewState.ContentFavorites(
                        drinks
                    )
                )
            }
        }
    }
}