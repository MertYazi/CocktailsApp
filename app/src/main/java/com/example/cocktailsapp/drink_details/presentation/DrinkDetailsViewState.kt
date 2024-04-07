package com.example.cocktailsapp.drink_details.presentation

/**
 * Created by Mert on 2024
 */
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

fun DrinkDetailsViewState.ContentDrinkDetails.updateShoppingItem(
    drink: DetailsViewState,
    position: String,
    isInShopping: Boolean
): DrinkDetailsViewState.ContentDrinkDetails {
    return DrinkDetailsViewState.ContentDrinkDetails(drinks =
    if (drink.drinks[0].idDrink == drinks.drinks[0].idDrink) {
        when (position) {
            "1" -> { drink.drinks[0].isShopping1 = isInShopping }
            "2" -> { drink.drinks[0].isShopping2 = isInShopping }
            "3" -> { drink.drinks[0].isShopping3 = isInShopping }
            "4" -> { drink.drinks[0].isShopping4 = isInShopping }
            "5" -> { drink.drinks[0].isShopping5 = isInShopping }
            "6" -> { drink.drinks[0].isShopping6 = isInShopping }
            "7" -> { drink.drinks[0].isShopping7 = isInShopping }
            "8" -> { drink.drinks[0].isShopping8 = isInShopping }
            "9" -> { drink.drinks[0].isShopping9 = isInShopping }
            "10" -> { drink.drinks[0].isShopping10 = isInShopping }
            "11" -> { drink.drinks[0].isShopping11 = isInShopping }
            "12" -> { drink.drinks[0].isShopping12 = isInShopping }
            "13" -> { drink.drinks[0].isShopping13 = isInShopping }
            "14" -> { drink.drinks[0].isShopping14 = isInShopping }
            "15" -> { drink.drinks[0].isShopping15 = isInShopping }
        }
        drink
    } else {
        drink
    }
    )
}