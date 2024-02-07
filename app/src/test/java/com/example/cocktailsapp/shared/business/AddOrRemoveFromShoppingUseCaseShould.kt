package com.example.cocktailsapp.shared.business

import com.example.cocktailsapp.drink_details.business.IsIngredientInShoppingUseCase
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddOrRemoveFromShoppingUseCaseShould {

    private val isIngredientInShoppingUseCase = mock<IsIngredientInShoppingUseCase>()
    private val cocktailsRepository = mock<CocktailsRepository>()
    private lateinit var useCase: AddOrRemoveFromShoppingUseCase
    private lateinit var shoppingItem: ShoppingItem

    @Before
    fun setup(){
        useCase =
            AddOrRemoveFromShoppingUseCase(
                isIngredientInShoppingUseCase,
                cocktailsRepository
            )
        shoppingItem = ShoppingItem()
        shoppingItem.drinkId = "123"
        shoppingItem.ingredientName = "Vodka"
    }

    @Test
    fun callAddMethodWhenIngredientIsNotInShopping() = runTest {
        whenever(isIngredientInShoppingUseCase.execute("123", "Vodka")).thenReturn(
            false
        )
        useCase.execute(
            shoppingItem
        )
        verify(cocktailsRepository).addToShopping(
            shoppingItem
        )
    }

    @Test
    fun callRemoveMethodWhenIngredientIsInShopping() = runTest {
        whenever(isIngredientInShoppingUseCase.execute("123", "Vodka")).thenReturn(
            true
        )
        useCase.execute(
            shoppingItem
        )
        verify(cocktailsRepository).removeFromShopping(
            shoppingItem.drinkId,
            shoppingItem.ingredientName
        )
    }
}