package com.example.cocktailsapp

import com.example.cocktailsapp.home.business.DrinkItem
import com.example.cocktailsapp.home.business.DrinkList
import com.example.cocktailsapp.home.data.DrinkListEntity
import javax.inject.Inject

class DrinkEntityToDrinkMapper @Inject constructor(
): Function1<DrinkListEntity, DrinkList> {
    override fun invoke(drinkListEntity: DrinkListEntity): DrinkList {
        return DrinkList(
            drinkListEntity.drinks.map {
                DrinkItem(
                    it.idDrink,
                    it.strDrink,
                    it.strDrinkThumb
                )
            } ?: listOf()
        )
    }
}