package com.example.cocktailsapp.shared.data.repository.api

import android.util.Log
import com.example.cocktailsapp.drink_details.business.DrinkDetailsEntityToDrinkDetailsMapper
import com.example.cocktailsapp.shared.business.DrinkDetailsItem
import com.example.cocktailsapp.shared.business.DrinkDetailsList
import com.example.cocktailsapp.drink_list.business.DrinkListEntityToDrinkListMapper
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.example.cocktailsapp.shared.business.ShoppingList
import com.example.cocktailsapp.home.business.AlcoholItem
import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryItem
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.shared.business.DrinkList
import com.example.cocktailsapp.home.business.GlassItem
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientEntityToIngredientMapper
import com.example.cocktailsapp.home.business.IngredientList
import com.example.cocktailsapp.shared.Constants.DB_ALCOHOLS
import com.example.cocktailsapp.shared.Constants.DB_CATEGORIES
import com.example.cocktailsapp.shared.Constants.DB_FAVORITES
import com.example.cocktailsapp.shared.Constants.DB_FIELD_DRINK_ID
import com.example.cocktailsapp.shared.Constants.DB_FIELD_INGREDIENT_NAME
import com.example.cocktailsapp.shared.Constants.DB_GLASSES
import com.example.cocktailsapp.shared.Constants.DB_SHOPPING
import com.example.cocktailsapp.shared.Constants.DRINK_ID
import com.example.cocktailsapp.shared.business.repository.CocktailsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailsRepositoryAPI @Inject constructor(
    private val service: CocktailsService,
    private val database: FirebaseFirestore,
    private val ingredientEntityToIngredientMapper: IngredientEntityToIngredientMapper,
    private val drinkListEntityToDrinkListMapper: DrinkListEntityToDrinkListMapper,
    private val drinkDetailsEntityToDrinkDetailsMapper: DrinkDetailsEntityToDrinkDetailsMapper
): CocktailsRepository {
    override suspend fun getCategories(): Result<CategoryList> {
        return withContext(Dispatchers.IO) {
            try {
                val categories = database.collection(DB_CATEGORIES)
                    .get()
                    .await()
                val categoriesList = CategoryList(
                    drinks = arrayListOf()
                )
                if (categories.documents.isNotEmpty()) {
                    for (category in categories) {
                        val categoryItem = category.toObject(CategoryItem::class.java)
                        categoriesList.drinks.add(categoryItem)
                    }
                    Result.Success(categoriesList)
                } else {
                    Result.Error(IllegalStateException("Empty categories list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerCategories", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getGlasses(): Result<GlassList> {
        return withContext(Dispatchers.IO) {
            try {
                val glasses = database.collection(DB_GLASSES)
                    .get()
                    .await()
                val glassesList = GlassList(
                    drinks = arrayListOf()
                )
                if (glasses.documents.isNotEmpty()) {
                    for (glass in glasses) {
                        val glassItem = glass.toObject(GlassItem::class.java)
                        glassesList.drinks.add(glassItem)
                    }
                    Result.Success(glassesList)
                } else {
                    Result.Error(IllegalStateException("Empty glasses list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerGlasses", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getIngredients(): Result<IngredientList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getIngredientList()
                val data = ingredientEntityToIngredientMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty ingredient list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerIngredient", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getAlcohols(): Result<AlcoholList> {
        return withContext(Dispatchers.IO) {
            try {
                val alcohols = database.collection(DB_ALCOHOLS)
                    .get()
                    .await()
                val alcoholsList = AlcoholList(
                    drinks = arrayListOf()
                )
                if (alcohols.documents.isNotEmpty()) {
                    for (alcohol in alcohols) {
                        val alcoholItem = alcohol.toObject(AlcoholItem::class.java)
                        alcoholsList.drinks.add(alcoholItem)
                    }
                    Result.Success(alcoholsList)
                } else {
                    Result.Error(IllegalStateException("Empty alcohol list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerAlcohol", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getDrinksByCategory(category: String): Result<DrinkList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getDrinksByCategory(category)
                val data = drinkListEntityToDrinkListMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty drinks by category list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerDrinkByCategory", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getDrinksByGlass(glass: String): Result<DrinkList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getDrinksByGlass(glass)
                val data = drinkListEntityToDrinkListMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty drinks by glass list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerDrinkByGlass", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getDrinksByIngredient(ingredient: String): Result<DrinkList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getDrinksByIngredient(ingredient)
                val data = drinkListEntityToDrinkListMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty drinks by ingredient list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerDrinkByIngredient", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getDrinksByAlcohol(alcohol: String): Result<DrinkList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getDrinksByAlcohol(alcohol)
                val data = drinkListEntityToDrinkListMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty drinks by alcohol list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerDrinkByAlcohol", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getDrinksById(id: String): Result<DrinkDetailsList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getDrinksById(id)
                val data = drinkDetailsEntityToDrinkDetailsMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty drink details by id list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerDrinkById", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun isFavorite(drinkId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val document = database.collection(DB_FAVORITES)
                .whereEqualTo(DRINK_ID, drinkId)
                .get()
                .await()
            if (document.isEmpty) {
                false
            } else {
                val drink: DrinkDetailsItem = document.documents[0].toObject(DrinkDetailsItem::class.java)!!
                drink.isFavorite
            }
        }
    }

    override suspend fun addToFavorites(drink: DrinkDetailsItem) {
        return withContext(Dispatchers.IO) {
            drink.isFavorite = true
            database.collection(DB_FAVORITES)
                .document(drink.idDrink)
                .set(drink)
                .await()
        }
    }

    override suspend fun removeFromFavorites(drinkId: String) {
        return withContext(Dispatchers.IO) {
            database.collection(DB_FAVORITES)
                .document(drinkId)
                .delete()
                .await()
        }
    }

    override suspend fun getFavorites(): Result<DrinkDetailsList> {
        return withContext(Dispatchers.IO) {
            try {
                val favorites = database.collection(DB_FAVORITES)
                    .get()
                    .await()
                val favoriteDrinkDetailsList = DrinkDetailsList(
                    drinks = arrayListOf()
                )
                if (favorites.documents.isNotEmpty()) {
                    for (favorite in favorites) {
                        val favoritesItem = favorite.toObject(DrinkDetailsItem::class.java)
                        favoriteDrinkDetailsList.drinks.add(favoritesItem)
                    }
                    Result.Success(favoriteDrinkDetailsList)
                } else {
                    Result.Error(IllegalStateException("Empty favorites list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerFavorites", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun searchDrinks(drink: String): Result<DrinkDetailsList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.searchDrinks(drink)
                val data = drinkDetailsEntityToDrinkDetailsMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty search drink details list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerSearch", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun isInShopping(drinkId: String, ingredientName: String): Boolean {
        return withContext(Dispatchers.IO) {
            val document = database.collection(DB_SHOPPING)
                .whereEqualTo(DB_FIELD_DRINK_ID, drinkId)
                .whereEqualTo(DB_FIELD_INGREDIENT_NAME, ingredientName)
                .get()
                .await()
            if (document.isEmpty) {
                false
            } else {
                val shopping: ShoppingItem = document.documents[0].toObject(ShoppingItem::class.java)!!
                shopping.isAddedToShopping
            }
        }
    }

    override suspend fun addToShopping(shoppingItem: ShoppingItem) {
        return withContext(Dispatchers.IO) {
            shoppingItem.isAddedToShopping = true
            database.collection(DB_SHOPPING)
                .document()
                .set(shoppingItem)
                .await()
        }
    }

    override suspend fun removeFromShopping(drinkId: String, ingredientName: String) {
        return withContext(Dispatchers.IO) {
            val doc = database.collection(DB_SHOPPING)
                .whereEqualTo(DB_FIELD_DRINK_ID, drinkId)
                .whereEqualTo(DB_FIELD_INGREDIENT_NAME, ingredientName)
                .get()
                .await()

            doc.documents[0].reference.delete().await()
        }
    }

    override suspend fun getShopping(): Result<ShoppingList> {
        return withContext(Dispatchers.IO) {
            try {
                val shopping = database.collection(DB_SHOPPING)
                    .get()
                    .await()
                val shoppingList = ShoppingList(
                    drinks = arrayListOf()
                )
                if (shopping.documents.isNotEmpty()) {
                    for (item in shopping) {
                        val shoppingItem = item.toObject(ShoppingItem::class.java)
                        shoppingList.drinks.add(shoppingItem)
                    }
                    Result.Success(shoppingList)
                } else {
                    Result.Error(IllegalStateException("Empty shopping list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerShopping", exception.message, exception)
                Result.Error(exception)
            }
        }
    }
}