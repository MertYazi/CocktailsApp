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
import com.example.cocktailsapp.databinding.ItemCategoryBinding
import com.example.cocktailsapp.home.business.CategoryItem
import com.example.cocktailsapp.shared.Constants.MY_CATEGORY
import com.example.cocktailsapp.shared.Constants.MY_IMAGE

class CategoryListAdapter(
    private val fragment: CategoryFragment
): RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    class ViewHolder(view: ItemCategoryBinding): RecyclerView.ViewHolder(view.root) {
        val categoryName = view.tvItemCategory
        val categoryImage = view.ivItemCategory
    }

    private val differCallback = object: DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(
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
        val category = differ.currentList[position]
        holder.apply {
            Glide.with(fragment)
                .load(category.image)
                .into(categoryImage)
            categoryName.text = category.name

            itemView.setOnClickListener {
                val bundle = bundleOf(
                    MY_CATEGORY to category.name,
                    MY_IMAGE to category.image
                )
                Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_homeFragment_to_drinkListFragment,
                    bundle
                )
            }
        }
    }
}