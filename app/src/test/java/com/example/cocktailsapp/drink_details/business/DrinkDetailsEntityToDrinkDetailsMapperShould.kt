package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.drink_details.data.DrinkDetailsItemEntity
import com.example.cocktailsapp.drink_details.data.DrinkDetailsListEntity
import com.example.cocktailsapp.shared.business.DrinkDetailsList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import junit.framework.TestCase.assertEquals
import org.junit.Before

@ExperimentalCoroutinesApi
class DrinkDetailsEntityToDrinkDetailsMapperShould {


    private lateinit var detailsEntity: DrinkDetailsListEntity

    private lateinit var mapper: DrinkDetailsEntityToDrinkDetailsMapper

    private lateinit var details: DrinkDetailsList

    @Before
    fun setup() {
        detailsEntity = DrinkDetailsListEntity(
            listOf(
                DrinkDetailsItemEntity(
                    "dateModified",
                    "idDrink",
                    "strAlcoholic",
                    "strCategory",
                    "strCreativeCommonsConfirmed",
                    "strDrink",
                    "strDrinkAlternate",
                    "strDrinkThumb",
                    "strGlass",
                    "strIBA",
                    "strImageAttribution",
                    "strImageSource",
                    " strIngredient1",
                    "strIngredient10",
                    "strIngredient11",
                    "strIngredient12",
                    "strIngredient13",
                    "strIngredient14",
                    "strIngredient15",
                    "strIngredient2",
                    "strIngredient3",
                    "strIngredient4",
                    "strIngredient5",
                    "strIngredient6",
                    "strIngredient7",
                    "strIngredient8",
                    "strIngredient9",
                    "strInstructions",
                    "strInstructionsDE",
                    "strInstructionsES",
                    "strInstructionsFR",
                    "strInstructionsIT",
                    "strInstructionsZHHANS",
                    "strInstructionsZHHANT",
                    "strMeasure1",
                    "strMeasure10",
                    "strMeasure11",
                    "strMeasure12",
                    "strMeasure13",
                    "strMeasure14",
                    "strMeasure15",
                    "strMeasure2",
                    "strMeasure3",
                    "strMeasure4",
                    "strMeasure5",
                    "strMeasure6",
                    "strMeasure7",
                    "strMeasure8",
                    "strMeasure9",
                    "strTags",
                    "strVideo"
                )
            )
        )
        mapper = DrinkDetailsEntityToDrinkDetailsMapper()
        details = mapper.invoke(detailsEntity)
    }

    @Test
    fun keepSameId() {
        assertEquals(detailsEntity.drinks[0].idDrink, details.drinks[0].idDrink)
    }

    @Test
    fun keepSameDate() {
        assertEquals(detailsEntity.drinks[0].dateModified, details.drinks[0].dateModified)
    }

    @Test
    fun matchWithName() {
        assertEquals(detailsEntity.drinks[0].strDrink, details.drinks[0].strDrink)
    }

    @Test
    fun matchWithAlcohol() {
        assertEquals(detailsEntity.drinks[0].strAlcoholic, details.drinks[0].strAlcoholic)
    }

    @Test
    fun matchWithCategory() {
        assertEquals(detailsEntity.drinks[0].strCategory, details.drinks[0].strCategory)
    }

    @Test
    fun matchWithGlass() {
        assertEquals(detailsEntity.drinks[0].strGlass, details.drinks[0].strGlass)
    }

}