package com.example.cocktailsapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.cocktailsapp.MainCoroutineScopeRule
import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientList
import com.example.cocktailsapp.home.presentation.AlcoholViewState
import com.example.cocktailsapp.home.presentation.CategoryViewState
import com.example.cocktailsapp.home.presentation.GlassViewState
import com.example.cocktailsapp.home.presentation.HomeViewModel
import com.example.cocktailsapp.home.presentation.HomeViewState
import com.example.cocktailsapp.home.presentation.IngredientViewState
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class HomeViewModelShould {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: CocktailsRepository = mock()
    private val dispatcher = StandardTestDispatcher()

    private val categoryList = mock<CategoryList>()
    private val expectedCategoryList = flowOf(Result.Success(categoryList))

    private val glassList = mock<GlassList>()
    private val expectedGlassList = flowOf(Result.Success(glassList))

    private val ingredientList = mock<IngredientList>()
    private val expectedIngredientList = flowOf(Result.Success(ingredientList))

    private val alcoholList = mock<AlcoholList>()
    private val expectedAlcoholList = flowOf(Result.Success(alcoholList))

    private val exception = flowOf(Result.Error(java.lang.RuntimeException("Something went wrong")))

    @Test
    fun getCategoryListFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCaseForCategoryList()
        getCategoryListViewStateValues(viewModel)
        verify(repository, Mockito.times(1)).getCategories()
    }

    @Test
    fun emitCategoryListFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCaseForCategoryList()
        val values = getCategoryListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.ContentCategory(
            CategoryViewState(
                categoryList.drinks
            )
        ))
    }

    @Test
    fun emitErrorWhenCategoryListReceiveError() {
        val viewModel = mockErrorCaseForCategoryList()
        val values = getCategoryListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.Error)
    }

    private fun getCategoryListViewStateValues(viewModel: HomeViewModel): MutableList<HomeViewState> {
        val values = mutableListOf<HomeViewState>()
        runBlocking {
            viewModel.viewStateCategory.test {
                for(i in 1 downTo 0) {
                    dispatcher.scheduler.advanceTimeBy(1000L)
                    val emission = awaitItem()
                    values.add(emission)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        return values
    }

    private fun mockSuccessfulCaseForCategoryList(): HomeViewModel {
        runBlocking {
            whenever(repository.getCategories()).thenReturn(
                expectedCategoryList
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    private fun mockErrorCaseForCategoryList(): HomeViewModel {
        runBlocking {
            whenever(repository.getCategories()).thenReturn(
                exception
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    @Test
    fun getGlassListFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCaseForGlassList()
        getGlassListViewStateValues(viewModel)
        verify(repository, Mockito.times(1)).getGlasses()
    }

    @Test
    fun emitGlassListFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCaseForGlassList()
        val values = getGlassListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.ContentGlass(
            GlassViewState(
                glassList.drinks
            )
        ))
    }

    @Test
    fun emitErrorWhenGlassListReceiveError() {
        val viewModel = mockErrorCaseForGlassList()
        val values = getGlassListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.Error)
    }

    private fun getGlassListViewStateValues(viewModel: HomeViewModel): MutableList<HomeViewState> {
        val values = mutableListOf<HomeViewState>()
        runBlocking {
            viewModel.viewStateGlass.test {
                for(i in 1 downTo 0) {
                    dispatcher.scheduler.advanceTimeBy(1000L)
                    val emission = awaitItem()
                    values.add(emission)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        return values
    }

    private fun mockSuccessfulCaseForGlassList(): HomeViewModel {
        runBlocking {
            whenever(repository.getGlasses()).thenReturn(
                expectedGlassList
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    private fun mockErrorCaseForGlassList(): HomeViewModel {
        runBlocking {
            whenever(repository.getGlasses()).thenReturn(
                exception
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    @Test
    fun getIngredientListFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCaseForIngredientList()
        getIngredientListViewStateValues(viewModel)
        verify(repository, Mockito.times(1)).getIngredients()
    }

    @Test
    fun emitIngredientListFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCaseForIngredientList()
        val values = getIngredientListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.ContentIngredient(
            IngredientViewState(
                ingredientList.drinks
            )
        ))
    }

    @Test
    fun emitErrorWhenIngredientListReceiveError() {
        val viewModel = mockErrorCaseForIngredientList()
        val values = getIngredientListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.Error)
    }

    private fun getIngredientListViewStateValues(viewModel: HomeViewModel): MutableList<HomeViewState> {
        val values = mutableListOf<HomeViewState>()
        runBlocking {
            viewModel.viewStateIngredient.test {
                for(i in 1 downTo 0) {
                    dispatcher.scheduler.advanceTimeBy(1000L)
                    val emission = awaitItem()
                    values.add(emission)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        return values
    }

    private fun mockSuccessfulCaseForIngredientList(): HomeViewModel {
        runBlocking {
            whenever(repository.getIngredients()).thenReturn(
                expectedIngredientList
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    private fun mockErrorCaseForIngredientList(): HomeViewModel {
        runBlocking {
            whenever(repository.getIngredients()).thenReturn(
                exception
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    @Test
    fun getAlcoholListFromRepository(): Unit = runBlocking {
        val viewModel = mockSuccessfulCaseForAlcoholList()
        getAlcoholListViewStateValues(viewModel)
        verify(repository, Mockito.times(1)).getAlcohols()
    }

    @Test
    fun emitAlcoholListFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCaseForAlcoholList()
        val values = getAlcoholListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.ContentAlcohol(
            AlcoholViewState(
                alcoholList.drinks
            )
        ))
    }

    @Test
    fun emitErrorWhenAlcoholListReceiveError() {
        val viewModel = mockErrorCaseForAlcoholList()
        val values = getAlcoholListViewStateValues(viewModel)
        assertEquals(values[0], HomeViewState.Loading)
        assertEquals(values[1], HomeViewState.Error)
    }

    private fun getAlcoholListViewStateValues(viewModel: HomeViewModel): MutableList<HomeViewState> {
        val values = mutableListOf<HomeViewState>()
        runBlocking {
            viewModel.viewStateAlcohol.test {
                for(i in 1 downTo 0) {
                    dispatcher.scheduler.advanceTimeBy(1000L)
                    val emission = awaitItem()
                    values.add(emission)
                }
                cancelAndConsumeRemainingEvents()
            }
        }
        return values
    }

    private fun mockSuccessfulCaseForAlcoholList(): HomeViewModel {
        runBlocking {
            whenever(repository.getAlcohols()).thenReturn(
                expectedAlcoholList
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

    private fun mockErrorCaseForAlcoholList(): HomeViewModel {
        runBlocking {
            whenever(repository.getAlcohols()).thenReturn(
                exception
            )
        }
        return HomeViewModel(repository, dispatcher)
    }

}