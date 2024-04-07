package com.example.cocktailsapp.home.business

import com.example.cocktailsapp.home.data.IngredientListEntity
import javax.inject.Inject

/**
 * Created by Mert on 2024
 */
class IngredientEntityToIngredientMapper @Inject constructor(
): Function1<IngredientListEntity, IngredientList> {

    override fun invoke(ingredientListEntity: IngredientListEntity): IngredientList {
        return IngredientList(
            ingredientListEntity.drinks.map {
                IngredientItem(
                    it.strIngredient1,
                    ""
                )
            } as ArrayList<IngredientItem>
        )
    }
}