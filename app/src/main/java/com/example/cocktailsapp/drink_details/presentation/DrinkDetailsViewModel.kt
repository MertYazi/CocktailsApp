package com.example.cocktailsapp.drink_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.CocktailsRepository
import com.example.cocktailsapp.Result
import com.example.cocktailsapp.drink_details.business.AddOrRemoveFromFavoritesUseCase
import com.example.cocktailsapp.drink_details.business.DrinkDetailsItem
import com.example.cocktailsapp.drink_details.business.DrinkDetailsList
import com.example.cocktailsapp.drink_details.business.IsDrinkInFavoritesUseCase
import com.example.cocktailsapp.home.business.DrinkItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrinkDetailsViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val isDrinkInFavoritesUseCase: IsDrinkInFavoritesUseCase,
    private val addOrRemoveFromFavoritesUseCase: AddOrRemoveFromFavoritesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateDrinkDetails = MutableLiveData<DrinkDetailsViewState>()
    val viewStateDrinkDetails: LiveData<DrinkDetailsViewState>
        get() = _viewStateDrinkDetails

    fun getDrinkDetails(id: String) = viewModelScope.launch(dispatcher) {
        _viewStateDrinkDetails.postValue(DrinkDetailsViewState.Loading)
        when(val result = repository.getDrinksById(id)) {
            is Result.Error -> {
                _viewStateDrinkDetails.postValue(DrinkDetailsViewState.Error)
            }
            is Result.Success -> {
                val drinkDetails = DetailsViewState(
                    result.data.drinks
                )
                drinkDetails.drinks[0].isFavorite = isDrinkInFavoritesUseCase.execute(result.data.drinks[0].idDrink)
                _viewStateDrinkDetails.postValue(
                    DrinkDetailsViewState.ContentDrinkDetails(
                        drinkDetails
                    )
                )
            }
        }

    }

    fun favoriteIconClicked(drink: DetailsViewState) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromFavoritesUseCase.execute(drink)
            val currentViewState = _viewStateDrinkDetails.value
            (currentViewState as? DrinkDetailsViewState.ContentDrinkDetails)?.let { content ->
                _viewStateDrinkDetails.postValue(
                    content.updateFavoriteDrink(
                        drink,
                        isDrinkInFavoritesUseCase.execute(drink.drinks[0].idDrink)
                    )
                )
            }
        }
    }
}