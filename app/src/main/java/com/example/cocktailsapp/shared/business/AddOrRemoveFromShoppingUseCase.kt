package com.example.cocktailsapp.shared.business

import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.drink_details.business.IsIngredientInShoppingUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by Mert on 2024
 */
class AddOrRemoveFromShoppingUseCase @Inject constructor(
    private val isIngredientInShoppingUseCase: IsIngredientInShoppingUseCase,
    private val repository: CocktailsRepository
) {
    suspend fun execute(shoppingItem: ShoppingItem) {
        val isInShopping = isIngredientInShoppingUseCase.execute(
            shoppingItem.drinkId,
            shoppingItem.ingredientName
        ).first()
        if(isInShopping){
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