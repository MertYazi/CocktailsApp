package com.example.cocktailsapp.shared.business

import com.example.cocktailsapp.drink_details.business.IsIngredientInShoppingUseCase
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Created by Mert on 2024
 */
@ExperimentalCoroutinesApi
class AddOrRemoveFromShoppingUseCaseShould {

    private val isIngredientInShoppingUseCase = mock<IsIngredientInShoppingUseCase>()
    private val cocktailsRepository = mock<CocktailsRepository>()
    private lateinit var useCase: AddOrRemoveFromShoppingUseCase
    private lateinit var shoppingItem: ShoppingItem
    private val drinkId = "1234"
    private val drinkName = "Vodka"

    @Before
    fun setup(){
        useCase =
            AddOrRemoveFromShoppingUseCase(
                isIngredientInShoppingUseCase,
                cocktailsRepository
            )
        shoppingItem = ShoppingItem()
        shoppingItem.drinkId = drinkId
        shoppingItem.ingredientName = drinkName
    }

    @Test
    fun callAddMethodWhenIngredientIsNotInShopping() = runBlocking {
        whenever(isIngredientInShoppingUseCase.execute(drinkId, drinkName)).thenReturn(
            flowOf(false)
        )
        useCase.execute(
            shoppingItem
        )
        verify(cocktailsRepository, times(1)).addToShopping(
            shoppingItem
        )
    }

    @Test
    fun callRemoveMethodWhenIngredientIsInShopping() = runBlocking {
        whenever(isIngredientInShoppingUseCase.execute(drinkId, drinkName)).thenReturn(
            flowOf(true)
        )
        useCase.execute(
            shoppingItem
        )
        verify(cocktailsRepository, times(1)).removeFromShopping(
            shoppingItem.drinkId,
            shoppingItem.ingredientName
        )
    }
}