package com.example.cocktailsapp.shopping.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.ItemShoppingBinding
import com.example.cocktailsapp.shopping.business.ShoppingListItem

/**
 * Created by Mert on 2024
 */
class ShoppingAdapter(
    private val fragment: ShoppingFragment
): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    class ViewHolder(view: ItemShoppingBinding): RecyclerView.ViewHolder(view.root) {
        val shoppingName = view.tvItemShopping
        val shoppingList = view.rvItemShopping
        val shoppingPin = view.ivItemShopping
        val shoppingCount = view.tvCountItemShopping
    }

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingListItem>() {
        override fun areItemsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ShoppingListItem, newItem: ShoppingListItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemShoppingBinding = ItemShoppingBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopping = differ.currentList[position]
        holder.apply {
            shoppingName.text = shopping.title

            val subShoppingAdapter = ShoppingSubAdapter(fragment)
            shopping.shoppingItems.sortBy { it.drinkName }
            subShoppingAdapter.differ.submitList(shopping.shoppingItems)
            shoppingList.layoutManager = LinearLayoutManager(fragment.context)
            shoppingList.adapter = subShoppingAdapter
            shoppingCount.text = shopping.shoppingItems.size.toString() +
                    if(shopping.shoppingItems.size > 1) " " +
                            fragment.resources.getString(R.string.items) else " " +
                            fragment.resources.getString(R.string.item)

            shoppingName.setOnClickListener {
                showHideItems()
            }
            shoppingPin.setOnClickListener {
                showHideItems()
            }
            shoppingCount.setOnClickListener {
                showHideItems()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun ViewHolder.showHideItems() {
        shoppingList.visibility = if (shoppingList.isShown) View.GONE else View.VISIBLE
        shoppingCount.visibility = if (shoppingList.isShown) View.GONE else View.VISIBLE
        if (shoppingList.isShown) {
            shoppingPin.setImageDrawable(ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_alcohol_red))
        } else {
            shoppingPin.setImageDrawable(ContextCompat.getDrawable(fragment.requireContext(), R.drawable.ic_alcohol))
        }
    }
}