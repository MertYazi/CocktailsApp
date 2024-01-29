package com.example.cocktailsapp.di

import com.example.cocktailsapp.shared.data.repository.api.APIClient
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.example.cocktailsapp.shared.data.repository.api.CocktailsRepositoryAPI
import com.example.cocktailsapp.shared.data.repository.api.CocktailsService
import com.example.cocktailsapp.drink_details.business.DrinkDetailsEntityToDrinkDetailsMapper
import com.example.cocktailsapp.drink_list.business.DrinkListEntityToDrinkListMapper
import com.example.cocktailsapp.home.business.IngredientEntityToIngredientMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class CocktailsModule {

    @Provides
    fun providesCocktailsService(
    ): CocktailsService = APIClient.getService()

    @Provides
    fun providesFireStoreInstance(
    ): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun providesCocktailsRepositoryAPI(
        service: CocktailsService,
        database: FirebaseFirestore,
        ingredientEntityToIngredientMapper: IngredientEntityToIngredientMapper,
        drinkListEntityToDrinkListMapper: DrinkListEntityToDrinkListMapper,
        drinkDetailsEntityToDrinkDetailsMapper: DrinkDetailsEntityToDrinkDetailsMapper
    ): CocktailsRepositoryAPI = CocktailsRepositoryAPI(
        service,
        database,
        ingredientEntityToIngredientMapper,
        drinkListEntityToDrinkListMapper,
        drinkDetailsEntityToDrinkDetailsMapper)

    @Provides
    fun providesCocktailsRepository(
        cocktailsRepositoryAPI: CocktailsRepositoryAPI
    ): CocktailsRepository = cocktailsRepositoryAPI

    @Provides
    fun providesIoDispatcher(
    ): CoroutineDispatcher = Dispatchers.IO

}