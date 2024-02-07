package com.example.cocktailsapp.home.business

import com.example.cocktailsapp.home.data.IngredientItemEntity
import com.example.cocktailsapp.home.data.IngredientListEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before

@ExperimentalCoroutinesApi
class IngredientEntityToIngredientMapperShould {


    private lateinit var ingredientListEntity: IngredientListEntity

    private lateinit var mapper: IngredientEntityToIngredientMapper

    private lateinit var ingredientList: IngredientList

    @Before
    fun setup() {
        ingredientListEntity = IngredientListEntity(
            listOf(
                IngredientItemEntity(
                    "strIngredient1"
                )
            )
        )
        mapper = IngredientEntityToIngredientMapper()
        ingredientList = mapper.invoke(ingredientListEntity)
    }

    @Test
    fun matchWithName() {
        assertEquals(ingredientListEntity.drinks[0].strIngredient1, ingredientList.drinks[0].strIngredient1)
    }

    @Test
    fun addEmptyImageThumb() {
        assertEquals("", ingredientList.drinks[0].imgIngredient)
    }

}