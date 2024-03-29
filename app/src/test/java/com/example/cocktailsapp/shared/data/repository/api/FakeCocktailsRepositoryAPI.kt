package com.example.cocktailsapp.shared.data.repository.api

import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientItem
import com.example.cocktailsapp.home.business.IngredientList
import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import com.example.cocktailsapp.shared.business.DrinkDetailsList
import com.example.cocktailsapp.shared.business.DrinkList
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.example.cocktailsapp.shared.business.ShoppingList
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

open class FakeCocktailsRepositoryAPI: CocktailsRepository {

    private val shoppingItems = mutableListOf(
        ShoppingItem(
            "120", "","","","vodka",
            "","",true
        )
    )
    val observableShoppingItems = MutableStateFlow<List<ShoppingItem>>(shoppingItems)

    private fun refreshStateFlow() {
        observableShoppingItems.value = shoppingItems
    }

    override suspend fun getIngredients(): Flow<Result<IngredientList>> {
        return flow {
            emit(
                Result.Success(
                    IngredientList(
                        (0..2).map {
                            IngredientItem(
                                "$it",
                                ""
                            )
                        } as ArrayList<IngredientItem>
                    )
                )
            )
        }
    }

    override suspend fun isInShopping(drinkId: String, ingredientName: String): Flow<Boolean> {
        return flow {
            emit(drinkId == "11118" && ingredientName == "margarita")
        }
    }

    override suspend fun addToShopping(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshStateFlow()
    }

    override suspend fun removeFromShopping(drinkId: String, ingredientName: String) {
        var shoppingItem = ShoppingItem()
        for (item in shoppingItems) {
            if (item.drinkId == drinkId && item.ingredientName == ingredientName) {
                shoppingItem = item
            }
        }
        shoppingItems.remove(shoppingItem)
        refreshStateFlow()
    }

    override suspend fun isFavorite(drinkId: String): Flow<Boolean> {
        return flow {
            emit(drinkId == "11118")
        }
    }

    override fun getCategories(): Flow<Result<CategoryList>> {
        TODO("Not yet implemented")
    }

    override fun getGlasses(): Flow<Result<GlassList>> {
        TODO("Not yet implemented")
    }

    override fun getAlcohols(): Flow<Result<AlcoholList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinksByCategory(category: String): Flow<Result<DrinkList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinksByGlass(glass: String): Flow<Result<DrinkList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinksByIngredient(ingredient: String): Flow<Result<DrinkList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinksByAlcohol(alcohol: String): Flow<Result<DrinkList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDrinksById(id: String): Flow<Result<DrinkDetailsList>> {
        TODO("Not yet implemented")
    }


    override suspend fun addToFavorites(drink: DrinkDetailsItem) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(drinkId: String) {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<Result<DrinkDetailsList>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchDrinks(drink: String): Flow<Result<DrinkDetailsList>> {
        TODO("Not yet implemented")
    }

    override fun getShopping(): Flow<Result<ShoppingList>> {
        TODO("Not yet implemented")
    }
}