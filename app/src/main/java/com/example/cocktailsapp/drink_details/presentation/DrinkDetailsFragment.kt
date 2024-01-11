package com.example.cocktailsapp.drink_details.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentDrinkDetailsBinding
import com.example.cocktailsapp.drink_details.business.ShoppingIngredientsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrinkDetailsFragment : Fragment() {

    private var _binding: FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkDetailsViewModel by viewModels()

    private lateinit var mDrinkDetails: DetailsViewState

    private var mShoppingIngredientsList: ArrayList<ShoppingIngredientsItem> = arrayListOf()

    private lateinit var mIngredientsAdapter: IngredientsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrinkDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DrinkDetailsFragmentArgs by navArgs()
        val id = args.myDrinkId
        Log.e("fuck", id)
        viewModel.viewStateDrinkDetails.observe(viewLifecycleOwner) { drinkDetails ->
            when(drinkDetails) {
                is DrinkDetailsViewState.ContentDrinkDetails -> {
                    mShoppingIngredientsList = arrayListOf()
                    mDrinkDetails = drinkDetails.drinks
                    if (mDrinkDetails.drinks[0].isFavorite) {
                        Glide.with(this)
                            .load(R.drawable.ic_favorite_selected)
                            .into(binding.ivFavoriteDrink)
                    } else {
                        Glide.with(this)
                            .load(R.drawable.ic_favorite_unselected)
                            .into(binding.ivFavoriteDrink)
                    }
                    binding.ivFavoriteDrink.visibility = View.VISIBLE
                    Glide.with(this)
                        .load(drinkDetails.drinks.drinks[0].strDrinkThumb)
                        .into(binding.ivDrinkDetails)
                    binding.tvNameDrinkDetails.text = mDrinkDetails.drinks[0].strDrink
                    binding.tvFilterDrinkDetails.text = mDrinkDetails.drinks[0].strCategory
                    binding.tvInstructionsDrinkDetails.text = mDrinkDetails.drinks[0].strInstructions

                    if (mDrinkDetails.drinks[0].strIngredient1.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient1,
                            mDrinkDetails.drinks[0].strMeasure1,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient2.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient2,
                            mDrinkDetails.drinks[0].strMeasure2,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient3.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient3,
                            mDrinkDetails.drinks[0].strMeasure3,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient4.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient4,
                            mDrinkDetails.drinks[0].strMeasure4,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient5.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient5,
                            mDrinkDetails.drinks[0].strMeasure5,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient6.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient6,
                            mDrinkDetails.drinks[0].strMeasure6,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient7.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient7,
                            mDrinkDetails.drinks[0].strMeasure7,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient8.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient8,
                            mDrinkDetails.drinks[0].strMeasure8,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient9.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient9,
                            mDrinkDetails.drinks[0].strMeasure9,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient10.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient10,
                            mDrinkDetails.drinks[0].strMeasure10,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient11.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient11,
                            mDrinkDetails.drinks[0].strMeasure11,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient12.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient12,
                            mDrinkDetails.drinks[0].strMeasure12,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient13.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient13,
                            mDrinkDetails.drinks[0].strMeasure13,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient14.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient14,
                            mDrinkDetails.drinks[0].strMeasure14,
                            false
                        ))
                    }
                    if (mDrinkDetails.drinks[0].strIngredient15.isNotEmpty()) {
                        mShoppingIngredientsList.add(ShoppingIngredientsItem(
                            mDrinkDetails.drinks[0].idDrink,
                            mDrinkDetails.drinks[0].strDrink,
                            mDrinkDetails.drinks[0].strIngredient15,
                            mDrinkDetails.drinks[0].strMeasure15,
                            false
                        ))
                    }


                    mIngredientsAdapter = IngredientsListAdapter(this)
                    mIngredientsAdapter.differ.submitList(mShoppingIngredientsList)
                    binding.rvInstructionsDrinkDetails.adapter = mIngredientsAdapter
                    binding.rvInstructionsDrinkDetails.layoutManager = LinearLayoutManager(activity)

                }
                is DrinkDetailsViewState.Error -> {
                    //
                }
                is DrinkDetailsViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        viewModel.getDrinkDetails(id)

        binding.apply {
            ivFavoriteDrink.setOnClickListener {
                viewModel.favoriteIconClicked(mDrinkDetails)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}