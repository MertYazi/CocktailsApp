package com.example.cocktailsapp.drink_list.business

import com.example.cocktailsapp.shared.business.DrinkItem
import com.example.cocktailsapp.shared.business.DrinkList
import com.example.cocktailsapp.drink_list.data.DrinkListEntity
import javax.inject.Inject

/**
 * Created by Mert on 2024
 */
class DrinkListEntityToDrinkListMapper @Inject constructor(
): Function1<DrinkListEntity, DrinkList> {
    override fun invoke(drinkListEntity: DrinkListEntity): DrinkList {
        return DrinkList(
            drinkListEntity.drinks.map {
                DrinkItem(
                    it.idDrink,
                    it.strDrink,
                    it.strDrinkThumb
                )
            } as ArrayList<DrinkItem>
        )
    }
}