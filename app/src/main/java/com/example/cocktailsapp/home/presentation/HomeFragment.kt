package com.example.cocktailsapp.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.ViewPagerAdapter
import com.example.cocktailsapp.databinding.FragmentHomeBinding
import com.example.cocktailsapp.home.business.AlcoholItem
import com.example.cocktailsapp.home.business.CategoryItem
import com.example.cocktailsapp.home.business.DrinkItem
import com.example.cocktailsapp.home.business.GlassItem
import com.example.cocktailsapp.home.business.IngredientItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var categoryList: ArrayList<CategoryItem>
    private lateinit var glassList: ArrayList<GlassItem>
    private lateinit var ingredientList: ArrayList<IngredientItem>
    private lateinit var alcoholList: ArrayList<AlcoholItem>
    private lateinit var drinkListByCategory: ArrayList<DrinkItem>
    private lateinit var drinkListByGlass: ArrayList<DrinkItem>
    private lateinit var drinkListByAlcohol: ArrayList<DrinkItem>
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList = arrayListOf()
        glassList = arrayListOf()
        ingredientList = arrayListOf()
        alcoholList = arrayListOf()
        drinkListByCategory = arrayListOf()
        drinkListByGlass = arrayListOf()
        drinkListByAlcohol = arrayListOf()

        adapter = ViewPagerAdapter(childFragmentManager, categoryList, glassList, ingredientList, alcoholList)
        adapter.addFragment(CategoryFragment(), "Category")
        adapter.addFragment(GlassFragment(), "Glass")
        adapter.addFragment(IngredientFragment(), "Ingredient")
        adapter.addFragment(AlcoholFragment(), "Alcohol")
        binding.viewpager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)

        viewModel.viewStateCategory.observe(viewLifecycleOwner) { categoriesList ->
            when(categoriesList) {
                is HomeViewState.ContentCategory -> {
                    categoryList.addAll(categoriesList.categories.drinks)
                    val firstCategory = categoryList[0].strCategory
                    viewModel.getDrinksByCategory(firstCategory)
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateGlass.observe(viewLifecycleOwner) { glassesList ->
            when(glassesList) {
                is HomeViewState.ContentGlass -> {
                    glassList.addAll(glassesList.glasses.drinks)
                    val firstGlass = glassList[0].strGlass
                    viewModel.getDrinksByGlass(firstGlass)
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateIngredient.observe(viewLifecycleOwner) { ingredientsList ->
            when(ingredientsList) {
                is HomeViewState.ContentIngredient -> {
                    ingredientList.addAll(ingredientsList.ingredients.drinks)
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateAlcohol.observe(viewLifecycleOwner) { alcoholsList ->
            when(alcoholsList) {
                is HomeViewState.ContentAlcohol -> {
                    alcoholList.addAll(alcoholsList.alcohols.drinks)
                    val firstAlcohol = alcoholList[0].strAlcoholic
                    viewModel.getDrinksByAlcohol(firstAlcohol)
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateDrinkByCategory.observe(viewLifecycleOwner) { drink ->
            when(drink) {
                is HomeViewState.ContentDrinkByCategory -> {
                    drinkListByCategory.add(drink.drinks.drinks.first())
                    if (categoryList.size == drinkListByCategory.size) {
                        for (i in categoryList.indices) {
                            categoryList[i].imgCategory = drinkListByCategory[i].strDrinkThumb
                        }
                    } else {
                        viewModel.getDrinksByCategory(categoryList[drinkListByCategory.size].strCategory)
                    }
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateDrinkByGlass.observe(viewLifecycleOwner) { drink ->
            when(drink) {
                is HomeViewState.ContentDrinkByGlass -> {
                    drinkListByGlass.add(drink.drinks.drinks.first())
                    if (glassList.size == drinkListByGlass.size) {
                        for (i in glassList.indices) {
                            glassList[i].imgGlass = drinkListByGlass[i].strDrinkThumb
                        }
                    } else {
                        viewModel.getDrinksByGlass(glassList[drinkListByGlass.size].strGlass)
                    }
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.viewStateDrinkByAlcohol.observe(viewLifecycleOwner) { drink ->
            when(drink) {
                is HomeViewState.ContentDrinkByAlcohol -> {
                    drinkListByAlcohol.add(drink.drinks.drinks.first())
                    if (alcoholList.size == drinkListByAlcohol.size) {
                        for (i in alcoholList.indices) {
                            alcoholList[i].imgAlcoholic = drinkListByAlcohol[i].strDrinkThumb
                        }
                    } else {
                        viewModel.getDrinksByAlcohol(alcoholList[drinkListByAlcohol.size].strAlcoholic)
                    }
                    adapter.notifyDataSetChanged()
                }
                is HomeViewState.Error -> {
                    //
                }
                is HomeViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        Glide.with(this)
            .load(R.drawable.cocktails_background)
            //.override(120, 120)
            .into(binding.ivHomeFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}