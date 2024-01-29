package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import javax.inject.Inject

class IsIngredientInShoppingUseCase @Inject constructor(
    private val repository: CocktailsRepository
) {
    suspend fun execute(drinkId: String, ingredientName: String): Boolean {
        return repository.isInShopping(drinkId, ingredientName)
    }
}