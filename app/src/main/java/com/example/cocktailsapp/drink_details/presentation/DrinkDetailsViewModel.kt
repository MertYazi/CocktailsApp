package com.example.cocktailsapp.drink_details.presentation

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

    private val _viewStateDrinkDetails = MutableStateFlow<DrinkDetailsViewState>(DrinkDetailsViewState.Loading)
    val viewStateDrinkDetails = _viewStateDrinkDetails.asStateFlow()

    fun getDrinkDetails(id: String) = viewModelScope.launch(dispatcher) {
        repository.getDrinksById(id).collect { result ->
            when(result) {
                is Result.Error -> {
                    _viewStateDrinkDetails.value = DrinkDetailsViewState.Error
                }
                is Result.Success -> {
                    val drinkDetails = DetailsViewState(
                        result.data.drinks
                    )
                    drinkDetails.drinks[0].isFavorite =
                        isDrinkInFavoritesUseCase.execute(result.data.drinks[0].idDrink).first()

                    /* Because of the API structure below part had to be done this long.
                     * That is two of the two part in whole project that is hideous.
                     * Feel free to commit if you think there is shorter and better way. */
                    if (result.data.drinks[0].strIngredient1.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping1 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient1).first() }
                    if (result.data.drinks[0].strIngredient2.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping2 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient2).first() }
                    if (result.data.drinks[0].strIngredient3.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping3 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient3).first() }
                    if (result.data.drinks[0].strIngredient4.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping4 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient4).first() }
                    if (result.data.drinks[0].strIngredient5.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping5 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient5).first() }
                    if (result.data.drinks[0].strIngredient6.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping6 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient6).first() }
                    if (result.data.drinks[0].strIngredient7.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping7 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient7).first() }
                    if (result.data.drinks[0].strIngredient8.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping8 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient8).first() }
                    if (result.data.drinks[0].strIngredient9.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping9 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient9).first() }
                    if (result.data.drinks[0].strIngredient10.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping10 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient10).first() }
                    if (result.data.drinks[0].strIngredient11.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping11 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient11).first() }
                    if (result.data.drinks[0].strIngredient12.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping12 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient12).first() }
                    if (result.data.drinks[0].strIngredient13.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping13 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient13).first() }
                    if (result.data.drinks[0].strIngredient14.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping14 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient14).first() }
                    if (result.data.drinks[0].strIngredient15.isNotEmpty()) {
                        drinkDetails.drinks[0].isShopping15 = isIngredientInShoppingUseCase.execute(
                            result.data.drinks[0].idDrink, result.data.drinks[0].strIngredient15).first() }
                    _viewStateDrinkDetails.value = DrinkDetailsViewState.ContentDrinkDetails(
                        drinkDetails
                    )
                }
            }
        }
    }

    fun favoriteIconClicked(drink: DetailsViewState) {
        viewModelScope.launch(dispatcher) {
            var isInFavorites = false
            val currentViewState = _viewStateDrinkDetails.value
            _viewStateDrinkDetails.value = DrinkDetailsViewState.Loading
            addOrRemoveFromFavoritesUseCase.execute(drink)
            isDrinkInFavoritesUseCase.execute(drink.drinks[0].idDrink).collect {
                isInFavorites = it
            }
            (currentViewState as? DrinkDetailsViewState.ContentDrinkDetails)?.let { content ->
                _viewStateDrinkDetails.value = content.updateFavoriteDrink(
                    drink,
                    isInFavorites
                )
            }
        }
    }

    fun addToShopIconClicked(drink: DetailsViewState, shoppingItem: ShoppingItem) {
        viewModelScope.launch(dispatcher) {
            var isInShopping = false
            val currentViewState = _viewStateDrinkDetails.value
            _viewStateDrinkDetails.value = DrinkDetailsViewState.Loading
            addOrRemoveFromShoppingUseCase.execute(shoppingItem)
            isIngredientInShoppingUseCase.execute(shoppingItem.drinkId, shoppingItem.ingredientName).collect {
                isInShopping = it
            }
            (currentViewState as? DrinkDetailsViewState.ContentDrinkDetails)?.let { content ->
                _viewStateDrinkDetails.value = content.updateShoppingItem(
                    drink,
                    shoppingItem.ingredientPosition,
                    isInShopping
                )
            }
        }
    }
}