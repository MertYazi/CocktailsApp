package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.shared.business.ShoppingItem
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class IsIngredientInShoppingUseCaseShould {

    private val cocktailsRepository = mock<CocktailsRepository>()
    private lateinit var useCase: IsIngredientInShoppingUseCase
    private lateinit var shoppingItem: ShoppingItem

    @Before
    fun setup(){
        useCase =
            IsIngredientInShoppingUseCase(
                cocktailsRepository
            )
        shoppingItem = ShoppingItem()
        shoppingItem.drinkId = "123"
        shoppingItem.drinkName = "Margarita"
    }

    @Test
    fun callExecuteMethodWhenDrinkIsInFavorites() = runTest {
        useCase.execute(
            "123",
            "Margarita"
        )
        verify(cocktailsRepository).isInShopping(
            shoppingItem.drinkId,
            shoppingItem.drinkName
        )
    }
    
}