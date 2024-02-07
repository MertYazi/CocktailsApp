package com.example.cocktailsapp.drink_list.business

import com.example.cocktailsapp.drink_list.data.DrinkItemEntity
import com.example.cocktailsapp.drink_list.data.DrinkListEntity
import com.example.cocktailsapp.shared.business.DrinkList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before

@ExperimentalCoroutinesApi
class DrinkListEntityToDrinkListMapperShould {


    private lateinit var drinkListEntity: DrinkListEntity

    private lateinit var mapper: DrinkListEntityToDrinkListMapper

    private lateinit var drinkList: DrinkList

    @Before
    fun setup() {
        drinkListEntity = DrinkListEntity(
            listOf(
                DrinkItemEntity(
                    "idDrink",
                    "strDrink",
                    "strDrinkThumb",
                )
            )
        )
        mapper = DrinkListEntityToDrinkListMapper()
        drinkList = mapper.invoke(drinkListEntity)
    }

    @Test
    fun keepSameId() {
        assertEquals(drinkListEntity.drinks[0].idDrink, drinkList.drinks[0].idDrink)
    }

    @Test
    fun matchWithName() {
        assertEquals(drinkListEntity.drinks[0].strDrink, drinkList.drinks[0].strDrink)
    }

    @Test
    fun matchWithDrinkThumb() {
        assertEquals(drinkListEntity.drinks[0].strDrinkThumb, drinkList.drinks[0].strDrinkThumb)
    }

}