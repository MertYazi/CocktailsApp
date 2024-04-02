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
import com.example.cocktailsapp.databinding.ItemAlcoholBinding
import com.example.cocktailsapp.home.business.AlcoholItem
import com.example.cocktailsapp.shared.Constants.MY_ALCOHOL
import com.example.cocktailsapp.shared.Constants.MY_IMAGE

class AlcoholListAdapter(
    private val fragment: AlcoholFragment
): RecyclerView.Adapter<AlcoholListAdapter.ViewHolder>() {

    class ViewHolder(view: ItemAlcoholBinding): RecyclerView.ViewHolder(view.root) {
        val alcoholImage = view.ivItemAlcohol
        val alcoholName = view.tvItemAlcohol
    }

    private val differCallback = object : DiffUtil.ItemCallback<AlcoholItem>() {
        override fun areItemsTheSame(oldItem: AlcoholItem, newItem: AlcoholItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: AlcoholItem, newItem: AlcoholItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlcoholBinding = ItemAlcoholBinding.inflate(
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
        val alcohol = differ.currentList[position]
        holder.apply {
            Glide.with(fragment)
                .load(alcohol.image)
                .into(alcoholImage)
            alcoholName.text = alcohol.name

            itemView.setOnClickListener {
                val bundle = bundleOf(
                    MY_IMAGE to alcohol.image,
                    MY_ALCOHOL to alcohol.name
                )
                Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_homeFragment_to_drinkListFragment,
                    bundle
                )
            }
        }
    }

}