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
import com.example.cocktailsapp.home.business.GlassItem
import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.IngredientEntityToIngredientMapper
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    /* With callbackFlow we're no longer need of a suspend keyword.
     * With snapshotListener viewModel will consume remote data changes automatically. */
    override fun getCategories() = callbackFlow {
        val snapshotListener = database.collection(DB_CATEGORIES)
            .addSnapshotListener { snapshot, _ ->
                val categoriesResponse = if (snapshot != null) {
                    val categoriesList = CategoryList(
                        drinks = arrayListOf()
                    )
                    categoriesList.drinks.addAll(snapshot.toObjects(CategoryItem::class.java))
                    Result.Success(categoriesList)
                } else {
                    Result.Error(IllegalStateException("Empty categories list"))
                }
                trySend(categoriesResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.flowOn(Dispatchers.IO)

    override fun getGlasses() = callbackFlow {
        val snapshotListener = database.collection(DB_GLASSES)
            .addSnapshotListener { snapshot, _ ->
                val glassesResponse = if (snapshot != null) {
                    val glassesList = GlassList(
                        drinks = arrayListOf()
                    )
                    glassesList.drinks.addAll(snapshot.toObjects(GlassItem::class.java))
                    Result.Success(glassesList)
                } else {
                    Result.Error(IllegalStateException("Empty glasses list"))
                }
                trySend(glassesResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getIngredients() = flow {
        try {
            val response = service.getIngredientList()
            val data = ingredientEntityToIngredientMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty ingredient list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerIngredient", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAlcohols() = callbackFlow {
        val snapshotListener = database.collection(DB_ALCOHOLS)
            .addSnapshotListener { snapshot, _ ->
                val alcoholsResponse = if (snapshot != null) {
                    val alcoholsList = AlcoholList(
                        drinks = arrayListOf()
                    )
                    alcoholsList.drinks.addAll(snapshot.toObjects(AlcoholItem::class.java))
                    Result.Success(alcoholsList)
                } else {
                    Result.Error(IllegalStateException("Empty alcohols list"))
                }
                trySend(alcoholsResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDrinksByCategory(category: String) = flow {
        try {
            val response = service.getDrinksByCategory(category)
            val data = drinkListEntityToDrinkListMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty drinks by category list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerDrinkByCategory", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDrinksByGlass(glass: String) = flow {
        try {
            val response = service.getDrinksByGlass(glass)
            val data = drinkListEntityToDrinkListMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty drinks by glass list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerDrinkByGlass", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDrinksByIngredient(ingredient: String) = flow {
        try {
            val response = service.getDrinksByIngredient(ingredient)
            val data = drinkListEntityToDrinkListMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty drinks by ingredient list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerDrinkByIngredient", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDrinksByAlcohol(alcohol: String) = flow {
        try {
            val response = service.getDrinksByAlcohol(alcohol)
            val data = drinkListEntityToDrinkListMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty drinks by alcohol list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerDrinkByAlcohol", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getDrinksById(id: String) = flow {
        try {
            val response = service.getDrinksById(id)
            val data = drinkDetailsEntityToDrinkDetailsMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty drink details by id list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerDrinkById", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun isFavorite(drinkId: String) = flow {
        val document = database.collection(DB_FAVORITES)
            .whereEqualTo(DRINK_ID, drinkId)
            .get()
            .await()
        if (document.isEmpty) {
            emit(false)
        } else {
            val drink: DrinkDetailsItem = document.documents[0].toObject(DrinkDetailsItem::class.java)!!
            emit(drink.isFavorite)
        }
    }.flowOn(Dispatchers.IO)

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

    override fun getFavorites() = callbackFlow {
        val snapshotListener = database.collection(DB_FAVORITES)
            .addSnapshotListener { snapshot, _ ->
                val favoritesResponse = if (snapshot != null) {
                    val favoriteDrinkDetailsList = DrinkDetailsList(
                        drinks = arrayListOf()
                    )
                    favoriteDrinkDetailsList.drinks.addAll(snapshot.toObjects(DrinkDetailsItem::class.java))
                    Result.Success(favoriteDrinkDetailsList)
                } else {
                    Result.Error(IllegalStateException("Empty favorites list"))
                }
                trySend(favoritesResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchDrinks(drink: String) = flow {
        try {
            val response = service.searchDrinks(drink)
            val data = drinkDetailsEntityToDrinkDetailsMapper.invoke(response)
            if (data.drinks.isNotEmpty()) {
                emit(Result.Success(data))
            } else {
                emit(Result.Error(IllegalStateException("Empty search drink details list")))
            }
        } catch (exception: Exception) {
            Log.e("NetworkLayerSearch", exception.message, exception)
            emit(Result.Error(exception))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun isInShopping(drinkId: String, ingredientName: String) = flow {
        val document = database.collection(DB_SHOPPING)
            .whereEqualTo(DB_FIELD_DRINK_ID, drinkId)
            .whereEqualTo(DB_FIELD_INGREDIENT_NAME, ingredientName)
            .get()
            .await()
        if (document.isEmpty) {
            emit(false)
        } else {
            val shopping: ShoppingItem = document.documents[0].toObject(ShoppingItem::class.java)!!
            emit(shopping.isAddedToShopping)
        }
    }.flowOn(Dispatchers.IO)

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

    override fun getShopping() = callbackFlow {
        val snapshotListener = database.collection(DB_SHOPPING)
            .addSnapshotListener { snapshot, _ ->
                val shoppingResponse = if (snapshot != null) {
                    val shoppingList = ShoppingList(
                        drinks = arrayListOf()
                    )
                    shoppingList.drinks.addAll(snapshot.toObjects(ShoppingItem::class.java))
                    Result.Success(shoppingList)
                } else {
                    Result.Error(IllegalStateException("Empty shopping list"))
                }
                trySend(shoppingResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }.flowOn(Dispatchers.IO)
}