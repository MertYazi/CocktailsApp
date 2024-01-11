package com.example.cocktailsapp

import android.util.Log
import com.example.cocktailsapp.drink_details.business.DrinkDetailsItem
import com.example.cocktailsapp.drink_details.business.DrinkDetailsList
import com.example.cocktailsapp.favorites.presentation.FavoriteDetailsViewState
import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.home.business.DrinkList
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailsRepositoryAPI @Inject constructor(
    private val service: CocktailsService,
    private val database: FirebaseFirestore,
    private val categoryEntityToCategoryMapper: CategoryEntityToCategoryMapper,
    private val glassEntityToGlassMapper: GlassEntityToGlassMapper,
    private val ingredientEntityToIngredientMapper: IngredientEntityToIngredientMapper,
    private val alcoholEntityToAlcoholMapper: AlcoholEntityToAlcoholMapper,
    private val drinkEntityToDrinkMapper: DrinkEntityToDrinkMapper,
    private val drinkDetailsEntityToDrinkDetailsMapper: DrinkDetailsEntityToDrinkDetailsMapper
): CocktailsRepository {
    override suspend fun getCategories(): Result<CategoryList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getCategoryList()
                val data = categoryEntityToCategoryMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty category list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerCategory", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getGlasses(): Result<GlassList> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getGlassList()
                val data = glassEntityToGlassMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty glass list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayerGlass", exception.message, exception)
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
                val response = service.getAlcoholList()
                val data = alcoholEntityToAlcoholMapper.invoke(response)
                if (data.drinks.isNotEmpty()) {
                    Result.Success(data)
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
                val data = drinkEntityToDrinkMapper.invoke(response)
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
                val data = drinkEntityToDrinkMapper.invoke(response)
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
                val data = drinkEntityToDrinkMapper.invoke(response)
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
                val data = drinkEntityToDrinkMapper.invoke(response)
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

    /*override fun getDrinks(result: (Result<DrinkDetailsList>) -> Unit) {
        //
    }

    override fun addDrink(drink: DrinkDetailsItem, result: (Result<String>) -> Unit) {
        val document = database.collection("drinks").document()
        drink.id = document.id
        document
            .set(drink)
            .addOnSuccessListener {
                result.invoke(
                    Result.Success("Note has been created successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Result.Error(
                        it
                    )
                )
            }
    }

    override fun updateDrink(drink: DrinkDetailsItem, result: (Result<String>) -> Unit) {
        //
    }*/

    override suspend fun isFavorite(drinkId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val document = database.collection("drinks")
                .whereEqualTo("idDrink", drinkId)
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
            database.collection("drinks")
                .document(drink.idDrink)
                .set(drink)
                .await()
        }
    }

    override suspend fun removeFromFavorites(drinkId: String) {
        return withContext(Dispatchers.IO) {
            database.collection("drinks")
                .document(drinkId)
                .delete()
                .await()
        }
    }

    override suspend fun getFavorites(): Result<DrinkDetailsList> {
        return withContext(Dispatchers.IO) {
            try {
                val favorites = database.collection("drinks")
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
}