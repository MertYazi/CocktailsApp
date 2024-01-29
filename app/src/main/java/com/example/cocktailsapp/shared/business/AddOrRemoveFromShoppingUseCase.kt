package com.example.cocktailsapp.shared.business

import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.drink_details.business.IsIngredientInShoppingUseCase
import javax.inject.Inject

class AddOrRemoveFromShoppingUseCase @Inject constructor(
    private val isIngredientInShoppingUseCase: IsIngredientInShoppingUseCase,
    private val repository: CocktailsRepository
) {
    suspend fun execute(shoppingItem: ShoppingItem) {
        if(isIngredientInShoppingUseCase.execute(shoppingItem.drinkId, shoppingItem.ingredientName)){
            repository.removeFromShopping(
                shoppingItem.drinkId, shoppingItem.ingredientName
            )
        } else {
            repository.addToShopping(
                shoppingItem
            )
        }
    }
}