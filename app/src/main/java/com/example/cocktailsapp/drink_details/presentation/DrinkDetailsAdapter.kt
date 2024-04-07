package com.example.cocktailsapp.drink_details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.ItemSubShoppingBinding
import com.example.cocktailsapp.shared.business.ShoppingItem

/**
 * Created by Mert on 2024
 */
class DrinkDetailsAdapter(
    private val fragment: DrinkDetailsFragment,
    val onItemClicked: (ShoppingItem) -> Unit
): RecyclerView.Adapter<DrinkDetailsAdapter.ViewHolder>() {

    class ViewHolder(view: ItemSubShoppingBinding): RecyclerView.ViewHolder(view.root) {
        val ingredientName = view.tvItemSubShoppingName
        val ingredientMeasure = view.tvItemSubShoppingMeasure
        val imageShopping = view.ivItemSubShopping
    }

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.ingredientName == newItem.ingredientName
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSubShoppingBinding = ItemSubShoppingBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredients = differ.currentList[position]
        holder.apply {
            ingredientName.text = ingredients.ingredientName
            ingredientMeasure.text = ingredients.ingredientMeasure
            if (ingredients.isAddedToShopping) {
                Glide.with(fragment)
                    .load(R.drawable.shopping_cart)
                    .into(imageShopping)
            } else {
                Glide.with(fragment)
                    .load(R.drawable.add_shopping_cart)
                    .into(imageShopping)
            }

            imageShopping.setOnClickListener {
                onItemClicked(ingredients)
            }
        }
    }

}