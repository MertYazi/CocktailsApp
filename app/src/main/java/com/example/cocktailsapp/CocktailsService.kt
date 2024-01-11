package com.example.cocktailsapp

import com.example.cocktailsapp.drink_details.data.DrinkDetailsListEntity
import com.example.cocktailsapp.home.data.AlcoholListEntity
import com.example.cocktailsapp.home.data.CategoryListEntity
import com.example.cocktailsapp.home.data.DrinkListEntity
import com.example.cocktailsapp.home.data.GlassListEntity
import com.example.cocktailsapp.home.data.IngredientListEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailsService {

    @GET("list.php?c=list")
    suspend fun getCategoryList(): CategoryListEntity

    @GET("list.php?g=list")
    suspend fun getGlassList(): GlassListEntity

    @GET("list.php?i=list")
    suspend fun getIngredientList(): IngredientListEntity

    @GET("list.php?a=list")
    suspend fun getAlcoholList(): AlcoholListEntity

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
}