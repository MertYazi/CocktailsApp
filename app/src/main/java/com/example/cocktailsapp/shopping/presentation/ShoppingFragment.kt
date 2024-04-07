package com.example.cocktailsapp.shopping.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentShoppingBinding
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.example.cocktailsapp.shopping.business.ShoppingListItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.stream.Collectors

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class ShoppingFragment : Fragment() {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by viewModels()

    private lateinit var mShoppingList: ShoppingDetailsViewState
    private lateinit var mShoppingAdapter: ShoppingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateShopping.collect { shopping ->
                    when (shopping) {
                        is ShoppingViewState.ContentShopping -> {
                            binding.loader.visibility = View.GONE
                            mShoppingList = shopping.drinks
                            populateUI(shopping.drinks.drinks)
                        }
                        is ShoppingViewState.Error -> {
                            binding.loader.visibility = View.GONE
                        }
                        is ShoppingViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewModel.getShopping()

        Glide.with(this)
            .load(R.drawable.cocktails_shopping)
            .into(binding.ivShoppingFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun populateUI(shopping: ArrayList<ShoppingItem>) {
        val arrayList = shopping.stream().map { it.drinkName }.distinct()
            .collect(Collectors.toList())
        val mains = arrayListOf<ShoppingListItem>()

        for (item in arrayList) {
            mains.add(
                ShoppingListItem(
                    item,
                    arrayListOf()
                )
            )
            for (shop in shopping) {
                if (shop.drinkName == item) {
                    mains.last().shoppingItems.add(shop)
                }
            }
        }
        mains.sortBy { it.title }

        mShoppingAdapter.differ.submitList(mains)

        binding.tvCountShoppingFragment.text =
            (mShoppingList.drinks.size ?: 0).toString() + " " + resources.getString(R.string.items_in) +
                    " " + (mains.size ?: 0).toString() + " " + resources.getString(R.string.cocktails)
    }

    private fun setupRecyclerViewAdapter() {
        mShoppingAdapter = ShoppingAdapter(this)
        binding.rvShoppingFragment.layoutManager = LinearLayoutManager(activity)
        binding.rvShoppingFragment.adapter = mShoppingAdapter
    }

    fun deleteShopping(shoppingItem: ShoppingItem) {
        viewModel.removeFromShopIconClicked(shoppingItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}