package com.example.cocktailsapp.drink_list.presentation

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentDrinkListBinding
import com.example.cocktailsapp.shared.business.DrinkItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class DrinkListFragment : Fragment() {

    private var _binding: FragmentDrinkListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkListViewModel by viewModels()

    private lateinit var adapter: DrinkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DrinkListFragmentArgs by navArgs()
        if (args.myCategory.isNotEmpty()) {
            adapter = DrinkListAdapter(this@DrinkListFragment, args.myCategory)
            setupRecyclerViewAdapter()
            viewModel.getDrinksByCategory(args.myCategory)
            binding.tvDrinkListFragment.text = args.myCategory
        }
        if (args.myGlass.isNotEmpty()) {
            adapter = DrinkListAdapter(this@DrinkListFragment, args.myGlass)
            setupRecyclerViewAdapter()
            viewModel.getDrinksByGlass(args.myGlass)
            binding.tvDrinkListFragment.text = args.myGlass
        }
        if (args.myIngredient.isNotEmpty()) {
            adapter = DrinkListAdapter(this@DrinkListFragment, args.myIngredient)
            setupRecyclerViewAdapter()
            viewModel.getDrinksByIngredient(args.myIngredient)
            binding.tvDrinkListFragment.text = args.myIngredient
        }
        if (args.myAlcohol.isNotEmpty()) {
            adapter = DrinkListAdapter(this@DrinkListFragment, args.myAlcohol)
            setupRecyclerViewAdapter()
            viewModel.getDrinksByAlcohol(args.myAlcohol)
            binding.tvDrinkListFragment.text = args.myAlcohol
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateDrinks.collect { drinks ->
                    when(drinks) {
                        is DrinkListViewState.ContentDrinkByCategory -> {
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByGlass -> {
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByIngredient -> {
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByAlcohol -> {
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.Error -> {
                            binding.loader.visibility = View.GONE
                        }
                        is DrinkListViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        Glide.with(this)
            .load(args.myImage)
            .into(binding.ivDrinkListFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun populateRecyclerView(drinks: ArrayList<DrinkItem>) {
        binding.loader.visibility = View.GONE
        drinks.sortBy { it.strDrink }
        adapter.differ.submitList(drinks)
        binding.tvCountDrinkListFragment.text = (drinks.size ?: 0).toString() +
                " " + resources.getString(R.string.cocktails)
    }

    private fun setupRecyclerViewAdapter() {
        binding.rvDrinkListFragment.adapter = adapter
        binding.rvDrinkListFragment.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}