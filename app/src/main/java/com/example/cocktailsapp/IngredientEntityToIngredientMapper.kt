package com.example.cocktailsapp

import com.example.cocktailsapp.home.business.IngredientList
import com.example.cocktailsapp.home.business.IngredientItem
import com.example.cocktailsapp.home.data.IngredientListEntity
import javax.inject.Inject

class IngredientEntityToIngredientMapper @Inject constructor(
): Function1<IngredientListEntity, IngredientList> {

    override fun invoke(ingredientListEntity: IngredientListEntity): IngredientList {
        return IngredientList(
            ingredientListEntity.drinks.map {
                IngredientItem(
                    it.strIngredient1,
                    ""
                )
            } ?: listOf()
        )
    }
}