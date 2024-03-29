package com.example.cocktailsapp.shared.business.repository

import com.example.cocktailsapp.shared.data.repository.api.Result
import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import com.example.cocktailsapp.shared.business.DrinkDetailsList
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.example.cocktailsapp.shared.business.ShoppingList
import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.shared.business.DrinkList
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientList
import kotlinx.coroutines.flow.Flow

interface CocktailsRepository {

    fun getCategories(): Flow<Result<CategoryList>>

    fun getGlasses(): Flow<Result<GlassList>>

    suspend fun getIngredients(): Flow<Result<IngredientList>>

    fun getAlcohols(): Flow<Result<AlcoholList>>

    suspend fun getDrinksByCategory(category: String): Flow<Result<DrinkList>>

    suspend fun getDrinksByGlass(glass: String): Flow<Result<DrinkList>>

    suspend fun getDrinksByIngredient(ingredient: String): Flow<Result<DrinkList>>

    suspend fun getDrinksByAlcohol(alcohol: String): Flow<Result<DrinkList>>

    suspend fun getDrinksById(id: String): Flow<Result<DrinkDetailsList>>

    suspend fun isFavorite(drinkId: String): Flow<Boolean>

    suspend fun addToFavorites(drink: DrinkDetailsItem)

    suspend fun removeFromFavorites(drinkId: String)

    fun getFavorites(): Flow<Result<DrinkDetailsList>>

    suspend fun searchDrinks(drink: String): Flow<Result<DrinkDetailsList>>

    suspend fun isInShopping(drinkId: String, ingredientName: String): Flow<Boolean>

    suspend fun addToShopping(shoppingItem: ShoppingItem)

    suspend fun removeFromShopping(drinkId: String, ingredientName: String)

    fun getShopping(): Flow<Result<ShoppingList>>
}