package com.example.cocktailsapp.shopping.business

import com.example.cocktailsapp.shared.business.ShoppingItem

data class ShoppingListItem(
    val title: String = "",
    val shoppingItems: ArrayList<ShoppingItem> = arrayListOf()
)
