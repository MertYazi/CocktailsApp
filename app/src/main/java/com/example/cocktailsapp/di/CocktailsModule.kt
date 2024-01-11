package com.example.cocktailsapp.di

import com.example.cocktailsapp.APIClient
import com.example.cocktailsapp.AlcoholEntityToAlcoholMapper
import com.example.cocktailsapp.CategoryEntityToCategoryMapper
import com.example.cocktailsapp.CocktailsRepository
import com.example.cocktailsapp.CocktailsRepositoryAPI
import com.example.cocktailsapp.CocktailsService
import com.example.cocktailsapp.DrinkDetailsEntityToDrinkDetailsMapper
import com.example.cocktailsapp.DrinkEntityToDrinkMapper
import com.example.cocktailsapp.GlassEntityToGlassMapper
import com.example.cocktailsapp.IngredientEntityToIngredientMapper
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
        categoryEntityToCategoryMapper: CategoryEntityToCategoryMapper,
        glassEntityToGlassMapper: GlassEntityToGlassMapper,
        ingredientEntityToIngredientMapper: IngredientEntityToIngredientMapper,
        alcoholEntityToAlcoholMapper: AlcoholEntityToAlcoholMapper,
        drinkEntityToDrinkMapper: DrinkEntityToDrinkMapper,
        drinkDetailsEntityToDrinkDetailsMapper: DrinkDetailsEntityToDrinkDetailsMapper
    ): CocktailsRepositoryAPI = CocktailsRepositoryAPI(
        service,
        database,
        categoryEntityToCategoryMapper,
        glassEntityToGlassMapper,
        ingredientEntityToIngredientMapper,
        alcoholEntityToAlcoholMapper,
        drinkEntityToDrinkMapper,
        drinkDetailsEntityToDrinkDetailsMapper)

    @Provides
    fun providesCocktailsRepository(
        cocktailsRepositoryAPI: CocktailsRepositoryAPI
    ): CocktailsRepository = cocktailsRepositoryAPI

    @Provides
    fun providesIoDispatcher(
    ): CoroutineDispatcher = Dispatchers.IO

}