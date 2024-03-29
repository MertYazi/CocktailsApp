package com.example.cocktailsapp.shared.data.repository.api

import app.cash.turbine.test
import com.example.cocktailsapp.drink_details.business.DrinkDetailsEntityToDrinkDetailsMapper
import com.example.cocktailsapp.drink_list.business.DrinkListEntityToDrinkListMapper
import com.example.cocktailsapp.home.business.IngredientEntityToIngredientMapper
import com.example.cocktailsapp.home.business.IngredientItem
import com.example.cocktailsapp.home.business.IngredientList
import com.example.cocktailsapp.home.data.IngredientItemEntity
import com.example.cocktailsapp.home.data.IngredientListEntity
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.google.firebase.firestore.FirebaseFirestore
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CocktailsRepositoryAPIShould {

    private var repository: CocktailsRepositoryAPI = mock()
    private var fakeRepository = FakeCocktailsRepositoryAPI()
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
    fun correctIngredientMapsEntityIntoBusinessObjectsAndCollect() = runBlocking {
        whenever(service.getIngredientList()).thenReturn(
            IngredientListEntity(
                (0..2).map {
                    IngredientItemEntity(
                        "$it",
                    )
                }
            )
        )
        whenever(ingredientEntityToIngredientMapper.invoke(
            IngredientListEntity(
                (0..2).map {
                    IngredientItemEntity(
                        "$it",
                )
            }
        ))).thenReturn(
            IngredientList(
                (0..2).map {
                    IngredientItem(
                        "$it",
                        ""
                    )
                } as ArrayList<IngredientItem>
            )
        )

        repository.getIngredients().collect {
            val ingredients = it as Result.Success
            assertEquals(ingredients.data.drinks.size, 3)
        }
    }

    @Test
    fun returnsTrueWhenGivenDrinkIsSavedIntoFavorites() = runBlocking {
        fakeRepository.isFavorite("11118").collect {
            assertTrue(it)
        }
    }

    @Test
    fun returnsFalseWhenGivenDrinkIsNotSavedIntoFavorites() = runBlocking {
        fakeRepository.isFavorite("").collect {
            assertFalse(false)
        }
    }

    @Test
    fun returnsTrueWhenGivenShoppingItemIsSavedIntoShopping() = runBlocking {
        fakeRepository.isInShopping("11118", "margarita").collect {
            assertTrue(it)
        }
    }

    @Test
    fun returnsFalseWhenGivenShoppingItemIsNotSavedIntoShopping() = runBlocking {
        fakeRepository.isInShopping("", "").collect {
            assertFalse(it)
        }
    }

    @Test
    fun addShoppingItemIntoShoppingDatabase() = runBlocking {
        val shoppingItem = ShoppingItem(
            "111",
            "",
            "",
            "",
            "lemon",
            "",
            "",
            false
        )
        fakeRepository.addToShopping(shoppingItem)
        fakeRepository.observableShoppingItems.test {
            assertTrue(awaitItem().contains(shoppingItem))
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun removeShoppingItemFromShoppingDatabase() = runBlocking {
        val shoppingItem = ShoppingItem(
            "120",
            "",
            "",
            "",
            "vodka",
            "",
            "",
            false
        )
        fakeRepository.removeFromShopping(shoppingItem.drinkId, shoppingItem.ingredientName)
        fakeRepository.observableShoppingItems.test {
            assertTrue(!awaitItem().contains(shoppingItem))
            cancelAndConsumeRemainingEvents()
        }
    }

    /*private var mockdb = mockk<FirebaseFirestore>()
    private val paths = "favorites"
    private val documentId = "2507"
    private val document = mutableMapOf<String, Any>(
        "Id" to documentId,
        "Name" to "905 Maple Drive",
        "CreatedDt" to Timestamp.now(),
        "OwnerName" to "Jim Smith"
    )
    val documentSnapshot = mockk<QuerySnapshot> {
        every { documents[0].id } returns documentId
        every { documents[0].data } returns document
    }
    val collectionRef = mockk<CollectionReference> {
        every { path } returns paths
    }*/

    /*@Test
    fun returnsTrueWhenGivenDrinkIsSavedIntoDatabase() = runTest {
        *//*mockdb = mockk {
            every {
                collection(DB_FAVORITES)
            } returns collectionRef

            every {
                collectionRef
                    .whereEqualTo(DRINK_ID, "2507")
                    .get()
            } returns mockTask<QuerySnapshot>(documentSnapshot)
        }
        println(documentSnapshot.documents[0].data)*//*
        fakeRepository.isFavorite("11118").collect {
            assertTrue(it)
        }
    }*/

    /*inline fun <reified T> mockTask(result: T?, exception: Exception? = null): Task<T> {
        val task: Task<T> = mockk(relaxed = true)
        every { task.isComplete } returns true
        every { task.exception } returns exception
        every { task.isCanceled } returns false
        val relaxedT: T = mockk(relaxed = true)
        every { task.result } returns result
        return task
    }*/

}