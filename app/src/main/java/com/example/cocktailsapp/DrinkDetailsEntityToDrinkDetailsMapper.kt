package com.example.cocktailsapp

import com.example.cocktailsapp.drink_details.business.DrinkDetailsItem
import com.example.cocktailsapp.drink_details.business.DrinkDetailsList
import com.example.cocktailsapp.drink_details.data.DrinkDetailsListEntity
import javax.inject.Inject

class DrinkDetailsEntityToDrinkDetailsMapper @Inject constructor(
): Function1<DrinkDetailsListEntity, DrinkDetailsList> {

    override fun invoke(drinkDetailsListEntity: DrinkDetailsListEntity): DrinkDetailsList {
        return DrinkDetailsList(
            drinkDetailsListEntity.drinks.map {
                DrinkDetailsItem(
                    it.dateModified,
                    it.idDrink ?: "",
                    it.strAlcoholic ?: "",
                    it.strCategory ?: "",
                    it.strCreativeCommonsConfirmed ?: "",
                    it.strDrink ?: "",
                    it.strDrinkAlternate ?: "",
                    it.strDrinkThumb ?: "",
                    it.strGlass ?: "",
                    it.strIBA ?: "",
                    it.strImageAttribution ?: "",
                    it.strImageSource ?: "",
                    it.strIngredient1 ?: "",
                    it.strIngredient10 ?: "",
                    it.strIngredient11 ?: "",
                    it.strIngredient12 ?: "",
                    it.strIngredient13 ?: "",
                    it.strIngredient14 ?: "",
                    it.strIngredient15 ?: "",
                    it.strIngredient2 ?: "",
                    it.strIngredient3 ?: "",
                    it.strIngredient4 ?: "",
                    it.strIngredient5 ?: "",
                    it.strIngredient6 ?: "",
                    it.strIngredient7 ?: "",
                    it.strIngredient8 ?: "",
                    it.strIngredient9 ?: "",
                    it.strInstructions ?: "",
                    it.strMeasure1 ?: "",
                    it.strMeasure10 ?: "",
                    it.strMeasure11 ?: "",
                    it.strMeasure12 ?: "",
                    it.strMeasure13 ?: "",
                    it.strMeasure14 ?: "",
                    it.strMeasure15 ?: "",
                    it.strMeasure2 ?: "",
                    it.strMeasure3 ?: "",
                    it.strMeasure4 ?: "",
                    it.strMeasure5 ?: "",
                    it.strMeasure6 ?: "",
                    it.strMeasure7 ?: "",
                    it.strMeasure8 ?: "",
                    it.strMeasure9 ?: "",
                    it.strTags ?: ""
                )
            } as ArrayList<DrinkDetailsItem>
        )
    }
}