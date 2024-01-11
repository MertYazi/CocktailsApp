package com.example.cocktailsapp.drink_details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsapp.databinding.ItemIngredientsBinding
import com.example.cocktailsapp.drink_details.business.ShoppingIngredientsItem

class IngredientsListAdapter(
    private val fragment: DrinkDetailsFragment
): RecyclerView.Adapter<IngredientsListAdapter.ViewHolder>() {

    class ViewHolder(view: ItemIngredientsBinding): RecyclerView.ViewHolder(view.root) {
        val ingredientsName = view.tvItemNameDrinkDetails
        val ingredientsMeasure = view.tvItemMeasureDrinkDetails
    }

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingIngredientsItem>() {
        override fun areItemsTheSame(oldItem: ShoppingIngredientsItem, newItem: ShoppingIngredientsItem): Boolean {
            return oldItem.ingredientName == newItem.ingredientName
        }

        override fun areContentsTheSame(oldItem: ShoppingIngredientsItem, newItem: ShoppingIngredientsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemIngredientsBinding = ItemIngredientsBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredients = differ.currentList[position]
        holder.apply {
            ingredientsName.text = ingredients.ingredientName
            ingredientsMeasure.text = ingredients.ingredientMeasure
        }
    }

}