package com.example.cocktailsapp.shopping.business

import com.example.cocktailsapp.shared.business.ShoppingItem

/**
 * Created by Mert on 2024
 */
data class ShoppingListItem(
    val title: String = "",
    val shoppingItems: ArrayList<ShoppingItem> = arrayListOf()
)
