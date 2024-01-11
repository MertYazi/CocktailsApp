package com.example.cocktailsapp.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailsapp.databinding.ItemGlassBinding
import com.example.cocktailsapp.home.business.GlassItem

class GlassListAdapter(
    private val fragment: GlassFragment
): RecyclerView.Adapter<GlassListAdapter.ViewHolder>() {

    class ViewHolder(view: ItemGlassBinding): RecyclerView.ViewHolder(view.root) {
        val glassName = view.tvItemGlass
        val glassImage = view.ivItemGlass
    }

    private var differCallback = object : DiffUtil.ItemCallback<GlassItem>() {
        override fun areItemsTheSame(oldItem: GlassItem, newItem: GlassItem): Boolean {
            return oldItem.strGlass == newItem.strGlass
        }

        override fun areContentsTheSame(oldItem: GlassItem, newItem: GlassItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemGlassBinding = ItemGlassBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val glass = differ.currentList[position]
        holder.apply {
            Glide.with(fragment)
                .load(glass.imgGlass)
                .into(glassImage)
            glassName.text = glass.strGlass

            itemView.setOnClickListener {
                fragment.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDrinkListFragment(
                        "",
                        glass.strGlass,
                        "",
                        "",
                        glass.imgGlass
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}