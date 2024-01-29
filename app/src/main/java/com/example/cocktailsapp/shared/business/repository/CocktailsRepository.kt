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

    suspend fun isFavorite(drinkId: String): Boolean

    suspend fun addToFavorites(drink: DrinkDetailsItem)

    suspend fun removeFromFavorites(drinkId: String)

    suspend fun getFavorites(): Result<DrinkDetailsList>

    suspend fun searchDrinks(drink: String): Result<DrinkDetailsList>

    suspend fun isInShopping(drinkId: String, ingredientName: String): Boolean

    suspend fun addToShopping(shoppingItem: ShoppingItem)

    suspend fun removeFromShopping(drinkId: String, ingredientName: String)

    suspend fun getShopping(): Result<ShoppingList>
}