package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Mert on 2024
 */
class IsDrinkInFavoritesUseCase @Inject constructor(
    private val repository: CocktailsRepository
) {
    suspend fun execute(drinkId: String): Flow<Boolean> {
        return repository.isFavorite(drinkId)
    }
}