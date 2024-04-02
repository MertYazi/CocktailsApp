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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentDrinkListBinding
import com.example.cocktailsapp.shared.Constants.MY_ALCOHOL
import com.example.cocktailsapp.shared.Constants.MY_CATEGORY
import com.example.cocktailsapp.shared.Constants.MY_GLASS
import com.example.cocktailsapp.shared.Constants.MY_IMAGE
import com.example.cocktailsapp.shared.Constants.MY_INGREDIENT
import com.example.cocktailsapp.shared.business.DrinkItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DrinkListFragment : Fragment() {

    private var _binding: FragmentDrinkListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkListViewModel by viewModels()

    private lateinit var category: String
    private lateinit var glass: String
    private lateinit var ingredient: String
    private lateinit var alcohol: String
    private lateinit var image: String

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

        arguments?.let {
            if (it.getString(MY_CATEGORY) != null) {
                category = it.getString(MY_CATEGORY)!!
                adapter = DrinkListAdapter(this@DrinkListFragment, category)
                setupRecyclerViewAdapter()
                viewModel.getDrinksByCategory(category)
                binding.tvDrinkListFragment.text = category
            }
            if (it.getString(MY_GLASS) != null) {
                glass = it.getString(MY_GLASS)!!
                adapter = DrinkListAdapter(this@DrinkListFragment, glass)
                setupRecyclerViewAdapter()
                viewModel.getDrinksByGlass(glass)
                binding.tvDrinkListFragment.text = glass
            }
            if (it.getString(MY_INGREDIENT) != null) {
                ingredient = it.getString(MY_INGREDIENT)!!
                adapter = DrinkListAdapter(this@DrinkListFragment, ingredient)
                setupRecyclerViewAdapter()
                viewModel.getDrinksByIngredient(ingredient)
                binding.tvDrinkListFragment.text = ingredient
            }
            if (it.getString(MY_ALCOHOL) != null) {
                alcohol = it.getString(MY_ALCOHOL)!!
                adapter = DrinkListAdapter(this@DrinkListFragment, alcohol)
                setupRecyclerViewAdapter()
                viewModel.getDrinksByAlcohol(alcohol)
                binding.tvDrinkListFragment.text = alcohol
            }
            if (it.getString(MY_IMAGE) != null) {
                image = it.getString(MY_IMAGE)!!
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateDrinks.collect { drinks ->
                    when(drinks) {
                        is DrinkListViewState.ContentDrinkByCategory -> {
                            binding.loader.visibility = View.GONE
                            drinks.drinks.drinks.sortBy { it.strDrink }
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByGlass -> {
                            binding.loader.visibility = View.GONE
                            drinks.drinks.drinks.sortBy { it.strDrink }
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByIngredient -> {
                            binding.loader.visibility = View.GONE
                            drinks.drinks.drinks.sortBy { it.strDrink }
                            populateRecyclerView(drinks.drinks.drinks)
                        }
                        is DrinkListViewState.ContentDrinkByAlcohol -> {
                            binding.loader.visibility = View.GONE
                            drinks.drinks.drinks.sortBy { it.strDrink }
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
            .load(image)
            .into(binding.ivDrinkListFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun populateRecyclerView(drinks: ArrayList<DrinkItem>) {
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