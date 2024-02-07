package com.example.cocktailsapp.shared.data.repository.api

import com.example.cocktailsapp.drink_details.business.DrinkDetailsEntityToDrinkDetailsMapper
import com.example.cocktailsapp.drink_list.business.DrinkListEntityToDrinkListMapper
import com.example.cocktailsapp.home.business.IngredientEntityToIngredientMapper
import com.example.cocktailsapp.home.data.IngredientItemEntity
import com.example.cocktailsapp.home.data.IngredientListEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CocktailsRepositoryAPIShould {

    private lateinit var repository: CocktailsRepositoryAPI
    private val service = mock<CocktailsService>()
    private val database = mock<FirebaseFirestore>()
    private val ingredientEntityToIngredientMapper: IngredientEntityToIngredientMapper = mock()
    private val drinkListEntityToDrinkListMapper: DrinkListEntityToDrinkListMapper = mock()
    private val drinkDetailsEntityToDrinkDetailsMapper: DrinkDetailsEntityToDrinkDetailsMapper = mock()

    @Before
    fun setup() {
        repository = CocktailsRepositoryAPI(
            service,
            database,
            ingredientEntityToIngredientMapper,
            drinkListEntityToDrinkListMapper,
            drinkDetailsEntityToDrinkDetailsMapper
        )
    }

    @Test
    fun correctMapsEntityIntoBusinessObjects() = runTest {
        whenever(service.getIngredientList()).thenReturn(
            IngredientListEntity(
                (0..2).map {
                    IngredientItemEntity(
                        "$it",
                    )
                }
            )
        )

        val ingredients = repository.getIngredients() as Result.Success

        assertEquals(ingredients.data.drinks.size, 3)
    }

    @Test
    fun returnsTrueWhenGivenDrinkIsSavedIntoDatabase() = runTest {
        whenever(repository.isFavorite("11118")).thenReturn(
            true
        )
        assertTrue(repository.isFavorite("11118"))
    }

    @Test
    fun returnsFalseWhenGivenDrinkIsNotSavedIntoDatabase() = runTest {
        whenever(repository.isFavorite("11118")).thenReturn(
            null
        )
        assertFalse(repository.isFavorite("11118"))
    }

}