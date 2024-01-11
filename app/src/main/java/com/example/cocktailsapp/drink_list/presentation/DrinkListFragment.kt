package com.example.cocktailsapp.drink_list.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.databinding.FragmentDrinkListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkListFragment : Fragment() {

    private var _binding: FragmentDrinkListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkListViewModel by viewModels()

    private lateinit var adapter: DrinkListAdapter

    private var category: String = ""
    private var glass: String = ""
    private var ingredient: String = ""
    private var alcohol: String = ""
    private var image: String = ""

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
        category = args.myCategory
        glass = args.myGlass
        ingredient = args.myIngredient
        alcohol = args.myAlcohol
        image = args.myImage

        viewModel.viewStateDrinks.observe(viewLifecycleOwner) { drinks ->
            when(drinks) {
                is DrinkListViewState.ContentDrinkByCategory -> {
                    adapter = DrinkListAdapter(this@DrinkListFragment, category)

                    adapter.differ.submitList(drinks.drinks.drinks)
                    binding.rvDrinkListFragment.adapter = adapter
                    binding.rvDrinkListFragment.layoutManager = LinearLayoutManager(activity)
                }
                is DrinkListViewState.ContentDrinkByGlass -> {
                    adapter = DrinkListAdapter(this@DrinkListFragment, glass)

                    adapter.differ.submitList(drinks.drinks.drinks)
                    binding.rvDrinkListFragment.adapter = adapter
                    binding.rvDrinkListFragment.layoutManager = LinearLayoutManager(activity)
                }
                is DrinkListViewState.ContentDrinkByIngredient -> {
                    adapter = DrinkListAdapter(this@DrinkListFragment, ingredient)

                    adapter.differ.submitList(drinks.drinks.drinks)
                    binding.rvDrinkListFragment.adapter = adapter
                    binding.rvDrinkListFragment.layoutManager = LinearLayoutManager(activity)
                }
                is DrinkListViewState.ContentDrinkByAlcohol -> {
                    adapter = DrinkListAdapter(this@DrinkListFragment, alcohol)

                    adapter.differ.submitList(drinks.drinks.drinks)
                    binding.rvDrinkListFragment.adapter = adapter
                    binding.rvDrinkListFragment.layoutManager = LinearLayoutManager(activity)
                }
                is DrinkListViewState.Error -> {

                }
                is DrinkListViewState.Loading -> {

                }
                else -> { }
            }
        }

        if (category.isNotEmpty()) {
            viewModel.getDrinksByCategory(category)
            binding.tvDrinkListFragment.text = category
        }
        if (glass.isNotEmpty()) {
            viewModel.getDrinksByGlass(glass)
            binding.tvDrinkListFragment.text = glass
        }
        if (ingredient.isNotEmpty()) {
            viewModel.getDrinksByIngredient(ingredient)
            binding.tvDrinkListFragment.text = ingredient
        }
        if (alcohol.isNotEmpty()) {
            viewModel.getDrinksByAlcohol(alcohol)
            binding.tvDrinkListFragment.text = alcohol
        }

        Glide.with(this)
            .load(image)
            //.override(90, 90)
            .into(binding.ivDrinkListFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}