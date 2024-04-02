package com.example.cocktailsapp.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.ItemIngredientBinding
import com.example.cocktailsapp.home.business.IngredientItem
import com.example.cocktailsapp.shared.Constants.INGREDIENT_EXTENSION
import com.example.cocktailsapp.shared.Constants.INGREDIENT_URL
import com.example.cocktailsapp.shared.Constants.MY_IMAGE
import com.example.cocktailsapp.shared.Constants.MY_INGREDIENT

class IngredientListAdapter(
    private val fragment: IngredientFragment
): RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {

    class ViewHolder(view: ItemIngredientBinding): RecyclerView.ViewHolder(view.root) {
        val ingredientImage = view.ivItemIngredient
        val ingredientName = view.tvItemIngredient
    }

    private val differCallback = object : DiffUtil.ItemCallback<IngredientItem>() {
        override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem.strIngredient1 == newItem.strIngredient1
        }

        override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemIngredientBinding = ItemIngredientBinding.inflate(
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
        val ingredient = differ.currentList[position]
        val ingredientThumb = INGREDIENT_URL +
                ingredient.strIngredient1 +
                INGREDIENT_EXTENSION
        holder.apply {
            Glide.with(fragment)
                .load(ingredientThumb)
                .into(ingredientImage)
            ingredientName.text = ingredient.strIngredient1

            itemView.setOnClickListener {
                val bundle = bundleOf(
                    MY_INGREDIENT to ingredient.strIngredient1,
                    MY_IMAGE to ingredient.imgIngredient
                )
                Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_homeFragment_to_drinkListFragment,
                    bundle
                )
            }
        }
    }
}