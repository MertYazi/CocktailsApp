package com.example.cocktailsapp.drink_details.presentation


sealed class DrinkDetailsViewState {

    object Loading: DrinkDetailsViewState()

    object Error: DrinkDetailsViewState()

    data class ContentDrinkDetails(val drinks: DetailsViewState): DrinkDetailsViewState()
}

fun DrinkDetailsViewState.ContentDrinkDetails.updateFavoriteDrink(
    drink: DetailsViewState,
    isFavorite: Boolean
): DrinkDetailsViewState.ContentDrinkDetails {
    return DrinkDetailsViewState.ContentDrinkDetails(drinks =
    if (drink.drinks[0].idDrink == drinks.drinks[0].idDrink) {
        drink.drinks[0].isFavorite = isFavorite
        drink
    } else {
        drink
    }
    )
}