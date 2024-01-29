package com.example.cocktailsapp.shopping.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import com.example.cocktailsapp.shared.business.AddOrRemoveFromShoppingUseCase
import com.example.cocktailsapp.shared.business.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: CocktailsRepository,
    private val addOrRemoveFromShoppingUseCase: AddOrRemoveFromShoppingUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateShopping = MutableLiveData<ShoppingViewState>()
    val viewStateShopping: LiveData<ShoppingViewState>
        get() = _viewStateShopping

    fun getShopping() = viewModelScope.launch(dispatcher) {
        _viewStateShopping.postValue(ShoppingViewState.Loading)
        when (val result = repository.getShopping()) {
            is Result.Error -> {
                _viewStateShopping.postValue(ShoppingViewState.Error)
            }
            is Result.Success -> {
                val drinks = ShoppingDetailsViewState(
                    result.data.drinks
                )
                _viewStateShopping.postValue(
                    ShoppingViewState.ContentShopping(
                        drinks
                    )
                )
            }
        }
    }

    fun removeFromShopIconClicked(shopping: ShoppingDetailsViewState, shoppingItem: ShoppingItem) {
        viewModelScope.launch(dispatcher) {
            addOrRemoveFromShoppingUseCase.execute(shoppingItem)
            val currentViewState = _viewStateShopping.value
            (currentViewState as? ShoppingViewState.ContentShopping)?.let { content ->
                _viewStateShopping.postValue(
                    content.updateShoppingItem(
                        shopping,
                        shoppingItem
                    )
                )
            }
        }
    }
}