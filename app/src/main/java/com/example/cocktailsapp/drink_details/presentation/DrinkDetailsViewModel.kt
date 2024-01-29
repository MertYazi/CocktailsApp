package com.example.cocktailsapp.drink_details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import com.example.cocktailsapp.drink_details.business.AddOrRemoveFromFavoritesUseCase
import com.example.cocktailsapp.shared.business.AddOrRemoveFromShoppingUseCase
import com.example.cocktailsapp.drink_details.business.IsDrinkInFavoritesUseCase
import com.example.cocktailsapp.drink_details.business.IsIngredientInShoppingUseCase
import com.example.cocktailsapp.shared.business.ShoppingItem
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
    private val isIngredientInShoppingUseCase: IsIngredientInShoppingUseCase,
    private val addOrRemoveFromShoppingUseCase: AddOrRemoveFromShoppingUseCase,
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

                /* Again similar problem with the usage as in DrinkDetailsFragment */
                if (result.data.drinks[0].strIngredient1.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping1 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient1) }
                if (result.data.drinks[0].strIngredient2.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping2 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient2) }
                if (result.data.drinks[0].strIngredient3.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping3 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient3) }
                if (result.data.drinks[0].strIngredient4.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping4 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient4) }
                if (result.data.drinks[0].strIngredient5.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping5 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient5) }
                if (result.data.drinks[0].strIngredient6.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping6 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient6) }
                if (result.data.drinks[0].strIngredient7.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping7 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient7) }
                if (result.data.drinks[0].strIngredient8.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping8 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient8) }
                if (result.data.drinks[0].strIngredient9.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping9 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient9) }
                if (result.data.drinks[0].strIngredient10.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping10 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient10) }
                if (result.data.drinks[0].strIngredient11.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping11 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient11) }
                if (result.data.drinks[0].strIngredient12.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping12 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient12) }
                if (result.data.drinks[0].strIngredient13.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping13 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient13) }
                if (result.data.drinks[0].strIngredient14.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping14 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient14) }
                if (result.data.drinks[0].strIngredient15.isNotEmpty()) {
                    drinkDetails.drinks[0].isShopping15 = isIngredientInShoppingUseCase.execute(
                        result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient15) }
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

    fun addToShopIconClicked(drink: DetailsViewState, shoppingItem: ShoppingItem) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromShoppingUseCase.execute(shoppingItem)
            val currentViewState = _viewStateDrinkDetails.value
            (currentViewState as? DrinkDetailsViewState.ContentDrinkDetails)?.let { content ->
                _viewStateDrinkDetails.postValue(
                    content.updateShoppingItem(
                        drink,
                        shoppingItem.ingredientPosition,
                        isIngredientInShoppingUseCase.execute(
                            shoppingItem.drinkId, shoppingItem.ingredientName)
                    )
                )
            }
        }
    }
}