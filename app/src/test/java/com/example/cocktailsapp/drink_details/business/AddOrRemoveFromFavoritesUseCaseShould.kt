package com.example.cocktailsapp.drink_details.business

import com.example.cocktailsapp.drink_details.presentation.DetailsViewState
import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Created by Mert on 2024
 */
@ExperimentalCoroutinesApi
class AddOrRemoveFromFavoritesUseCaseShould {

    private val isDrinkInFavoritesUseCase = mock<IsDrinkInFavoritesUseCase>()
    private val cocktailsRepository = mock<CocktailsRepository>()
    private lateinit var useCase: AddOrRemoveFromFavoritesUseCase
    private lateinit var detailsViewState: DetailsViewState

    @Before
    fun setup(){
        useCase =
            AddOrRemoveFromFavoritesUseCase(
                isDrinkInFavoritesUseCase,
                cocktailsRepository
            )
        detailsViewState = DetailsViewState(
            arrayListOf(DrinkDetailsItem())
        )
    }

    @Test
    fun callAddMethodWhenDrinkIsNotInFavorites() = runTest {
        whenever(isDrinkInFavoritesUseCase.execute(any())).thenReturn(
            flowOf(false)
        )
        useCase.execute(
            detailsViewState
        )
        verify(cocktailsRepository).addToFavorites(
            detailsViewState.drinks[0]
        )
    }

    @Test
    fun callRemoveMethodWhenDrinkIsInFavorites() = runTest {
        whenever(isDrinkInFavoritesUseCase.execute(any())).thenReturn(
            flowOf(true)
        )
        useCase.execute(
            detailsViewState
        )
        verify(cocktailsRepository).removeFromFavorites(
            detailsViewState.drinks[0].idDrink
        )
    }
}