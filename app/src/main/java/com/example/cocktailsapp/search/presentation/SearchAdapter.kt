package com.example.cocktailsapp.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailsapp.databinding.ItemDrinkListBinding
import com.example.cocktailsapp.shared.business.DrinkDetailsItem

class SearchAdapter(
    private val fragment: SearchFragment
): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(view: ItemDrinkListBinding): RecyclerView.ViewHolder(view.root) {
        val imageDrinkList = view.ivItemDrinkList
        val nameDrinkList = view.tvItemNameDrinkList
        val filterDrinkList = view.tvItemFilterDrinkList
    }

    private val differCallback = object : DiffUtil.ItemCallback<DrinkDetailsItem>() {
        override fun areItemsTheSame(oldItem: DrinkDetailsItem, newItem: DrinkDetailsItem): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: DrinkDetailsItem, newItem: DrinkDetailsItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDrinkListBinding.inflate(
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
        val drink = differ.currentList[position]
        holder.apply {
            Glide.with(fragment)
                .load(drink.strDrinkThumb)
                .into(imageDrinkList)
            nameDrinkList.text = drink.strDrink
            filterDrinkList.text = drink.strCategory

            itemView.setOnClickListener {
                fragment.findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToDrinkDetailsFragment(
                        drink.idDrink
                    )
                )
            }
        }
    }

}