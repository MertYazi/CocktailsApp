package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.drink_details.presentation.DetailsViewState
import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class IsDrinkInFavoritesUseCaseShould {

    private val cocktailsRepository = mock<CocktailsRepository>()
    private lateinit var useCase: IsDrinkInFavoritesUseCase
    private lateinit var detailsViewState: DetailsViewState
    private lateinit var drinkDetailsItem: DrinkDetailsItem

    @Before
    fun setup(){
        useCase =
            IsDrinkInFavoritesUseCase(
                cocktailsRepository
            )
        drinkDetailsItem = DrinkDetailsItem()
        drinkDetailsItem.idDrink = "123"
        detailsViewState = DetailsViewState(
            arrayListOf(
                drinkDetailsItem
            )
        )
    }

    @Test
    fun callExecuteMethodWhenDrinkIsInFavorites() = runTest {
        useCase.execute(
            "123"
        )
        verify(cocktailsRepository).isFavorite(
            detailsViewState.drinks[0].idDrink
        )
    }

}