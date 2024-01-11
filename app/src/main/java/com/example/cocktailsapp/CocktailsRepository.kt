package com.example.cocktailsapp

import com.example.cocktailsapp.drink_details.business.DrinkDetailsItem
import com.example.cocktailsapp.drink_details.business.DrinkDetailsList
import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.home.business.DrinkItem
import com.example.cocktailsapp.home.business.DrinkList
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientList

interface CocktailsRepository {

    suspend fun getCategories(): Result<CategoryList>

    suspend fun getGlasses(): Result<GlassList>

    suspend fun getIngredients(): Result<IngredientList>

    suspend fun getAlcohols(): Result<AlcoholList>

    suspend fun getDrinksByCategory(category: String): Result<DrinkList>

    suspend fun getDrinksByGlass(glass: String): Result<DrinkList>

    suspend fun getDrinksByIngredient(ingredient: String): Result<DrinkList>

    suspend fun getDrinksByAlcohol(alcohol: String): Result<DrinkList>

    suspend fun getDrinksById(id: String): Result<DrinkDetailsList>

    /*fun getDrinks(result: (Result<DrinkDetailsList>) -> Unit)
    fun addDrink(drink: DrinkDetailsItem, result: (Result<String>) -> Unit)
    fun updateDrink(drink: DrinkDetailsItem, result: (Result<String>) -> Unit)*/

    suspend fun isFavorite(drinkId : String): Boolean

    suspend fun addToFavorites(drink : DrinkDetailsItem)

    suspend fun removeFromFavorites(drinkId : String)

    suspend fun getFavorites(): Result<DrinkDetailsList>
}