package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.CocktailsRepository
import com.example.cocktailsapp.drink_details.presentation.DetailsViewState
import javax.inject.Inject

class AddOrRemoveFromFavoritesUseCase @Inject constructor(
    private val isDrinkInFavoritesUseCase: IsDrinkInFavoritesUseCase,
    private val repository: CocktailsRepository
) {
    suspend fun execute(drink: DetailsViewState) {
        if(isDrinkInFavoritesUseCase.execute(drink.drinks[0].idDrink)){
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