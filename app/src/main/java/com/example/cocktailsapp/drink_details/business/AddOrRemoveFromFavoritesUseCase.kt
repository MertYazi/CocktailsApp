package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.drink_details.presentation.DetailsViewState
import javax.inject.Inject

class AddOrRemoveFromFavoritesUseCase @Inject constructor(
    private val isDrinkInFavoritesUseCase: IsDrinkInFavoritesUseCase,
    private val repository: CocktailsRepository
) {
    suspend fun execute(drink: DetailsViewState) {
        var isInFavorites = false
        isDrinkInFavoritesUseCase.execute(drink.drinks[0].idDrink).collect {
            isInFavorites = it
        }
        if(isInFavorites){
            repository.removeFromFavorites(
                drink.drinks[0].idDrink
            )
        } else {
            repository.addToFavorites(
                drink.drinks[0]
            )
        }
    }
}