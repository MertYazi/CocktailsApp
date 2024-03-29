package com.example.cocktailsapp.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import com.example.cocktailsapp.shared.business.AddOrRemoveFromShoppingUseCase
import com.example.cocktailsapp.shared.business.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val addOrRemoveFromShoppingUseCase: AddOrRemoveFromShoppingUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateShopping = MutableStateFlow<ShoppingViewState>(ShoppingViewState.Loading)
    val viewStateShopping = _viewStateShopping.asStateFlow()

    fun getShopping() = viewModelScope.launch(dispatcher) {
        repository.getShopping().collect { result ->
            when (result) {
                is Result.Error -> {
                    _viewStateShopping.value = ShoppingViewState.Error
                }
                is Result.Success -> {
                    val drinks = ShoppingDetailsViewState(
                        result.data.drinks
                    )
                    _viewStateShopping.value = ShoppingViewState.ContentShopping(
                        drinks
                    )
                }
            }
        }
    }

    fun removeFromShopIconClicked(shoppingItem: ShoppingItem) {
        viewModelScope.launch(dispatcher) {
            _viewStateShopping.value = ShoppingViewState.Loading
            addOrRemoveFromShoppingUseCase.execute(shoppingItem)
        }
    }
}