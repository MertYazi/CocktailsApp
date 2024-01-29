package com.example.cocktailsapp.shared.data.repository.api

import com.example.cocktailsapp.drink_details.data.DrinkDetailsListEntity
import com.example.cocktailsapp.drink_list.data.DrinkListEntity
import com.example.cocktailsapp.home.data.IngredientListEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsService {

    @GET("list.php?i=list")
    suspend fun getIngredientList(): IngredientListEntity

    @GET("filter.php")
    suspend fun getDrinksByCategory(
        @Query("c") category: String
    ): DrinkListEntity

    @GET("filter.php")
    suspend fun getDrinksByGlass(
        @Query("g") glass: String
    ): DrinkListEntity

    @GET("filter.php")
    suspend fun getDrinksByAlcohol(
        @Query("a") alcohol: String
    ): DrinkListEntity

    @GET("filter.php")
    suspend fun getDrinksByIngredient(
        @Query("i") ingredient: String
    ): DrinkListEntity

    @GET("lookup.php")
    suspend fun getDrinksById(
        @Query("i") id: String
    ): DrinkDetailsListEntity

    @GET("search.php")
    suspend fun searchDrinks(
        @Query("s") drink: String
    ): DrinkDetailsListEntity
}