package com.example.cocktailsapp.drink_details.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentDrinkDetailsBinding
import com.example.cocktailsapp.shared.business.ShoppingItem
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DrinkDetailsFragment : Fragment() {

    private var _binding: FragmentDrinkDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DrinkDetailsViewModel by viewModels()

    private lateinit var mDrinkDetails: DetailsViewState

    private var mShoppingIngredientsList: ArrayList<ShoppingItem> = arrayListOf()

    private lateinit var id: String

    private lateinit var mIngredientsAdapter: DrinkDetailsAdapter

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
        id = args.myDrinkId

        setupBottomSheet()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateDrinkDetails.collect { drinkDetails ->
                    when(drinkDetails) {
                        is DrinkDetailsViewState.ContentDrinkDetails -> {
                            binding.loader.visibility = View.GONE
                            mShoppingIngredientsList = arrayListOf()
                            mDrinkDetails = drinkDetails.drinks
                            populateUI()
                        }
                        is DrinkDetailsViewState.Error -> {
                            binding.loader.visibility = View.GONE
                        }
                        is DrinkDetailsViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewModel.getDrinkDetails(id)

        binding.apply {
            ivFavoriteDrink.setOnClickListener {
                viewModel.favoriteIconClicked(mDrinkDetails)
            }
        }
    }

    private fun setupBottomSheet() {
        val bottomSheet = binding.bsDrinkDetails
        val mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        mBottomSheetBehavior.halfExpandedRatio = 0.65F
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun populateUI() {
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
            .load(mDrinkDetails.drinks[0].strDrinkThumb)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                @RequiresApi(Build.VERSION_CODES.Q)
                @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?,
                    dataSource: DataSource, isFirstResource: Boolean
                ): Boolean {
                    Palette.from(resource.toBitmap()).generate { palette ->
                        palette?.let {
                            val intColor = it.vibrantSwatch?.rgb ?: 0
                            val hexColor = String.format("#%06X", (intColor))
                            binding.clSheetContentsDrinkDetails.background.colorFilter =
                                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                    Color.parseColor(hexColor), BlendModeCompat.SRC_ATOP)
                            binding.clLikeDrinkDetails.background.colorFilter =
                                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                    Color.parseColor(hexColor), BlendModeCompat.SRC_ATOP)
                        }
                    }
                    return false
                }
            })
            .into(binding.ivDrinkDetails)

        binding.tvNameDrinkDetails.text = mDrinkDetails.drinks[0].strDrink
        binding.tvFilterDrinkDetails.text = mDrinkDetails.drinks[0].strCategory
        binding.tvInstructionsDrinkDetails.text = mDrinkDetails.drinks[0].strInstructions

        /* Because of the API structure below part had to be done this long.
         * That is one of the two part in whole project that is hideous.
         * Feel free to commit if you think there is shorter and better way. */
        if (mDrinkDetails.drinks[0].strIngredient1.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient1,
                    mDrinkDetails.drinks[0].strMeasure1,
                    "1",
                    mDrinkDetails.drinks[0].isShopping1
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient2.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient2,
                    mDrinkDetails.drinks[0].strMeasure2,
                    "2",
                    mDrinkDetails.drinks[0].isShopping2
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient3.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient3,
                    mDrinkDetails.drinks[0].strMeasure3,
                    "3",
                    mDrinkDetails.drinks[0].isShopping3
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient4.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient4,
                    mDrinkDetails.drinks[0].strMeasure4,
                    "4",
                    mDrinkDetails.drinks[0].isShopping4
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient5.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient5,
                    mDrinkDetails.drinks[0].strMeasure5,
                    "5",
                    mDrinkDetails.drinks[0].isShopping5
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient6.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient6,
                    mDrinkDetails.drinks[0].strMeasure6,
                    "6",
                    mDrinkDetails.drinks[0].isShopping6
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient7.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient7,
                    mDrinkDetails.drinks[0].strMeasure7,
                    "7",
                    mDrinkDetails.drinks[0].isShopping7
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient8.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient8,
                    mDrinkDetails.drinks[0].strMeasure8,
                    "8",
                    mDrinkDetails.drinks[0].isShopping8
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient9.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient9,
                    mDrinkDetails.drinks[0].strMeasure9,
                    "9",
                    mDrinkDetails.drinks[0].isShopping9
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient10.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient10,
                    mDrinkDetails.drinks[0].strMeasure10,
                    "10",
                    mDrinkDetails.drinks[0].isShopping10
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient11.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient11,
                    mDrinkDetails.drinks[0].strMeasure11,
                    "11",
                    mDrinkDetails.drinks[0].isShopping11
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient12.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient12,
                    mDrinkDetails.drinks[0].strMeasure12,
                    "12",
                    mDrinkDetails.drinks[0].isShopping12
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient13.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient13,
                    mDrinkDetails.drinks[0].strMeasure13,
                    "13",
                    mDrinkDetails.drinks[0].isShopping13
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient14.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient14,
                    mDrinkDetails.drinks[0].strMeasure14,
                    "14",
                    mDrinkDetails.drinks[0].isShopping14
                )
            )
        }
        if (mDrinkDetails.drinks[0].strIngredient15.isNotEmpty()) {
            mShoppingIngredientsList.add(
                ShoppingItem(
                    mDrinkDetails.drinks[0].idDrink,
                    mDrinkDetails.drinks[0].strDrink,
                    mDrinkDetails.drinks[0].strCategory,
                    mDrinkDetails.drinks[0].strAlcoholic,
                    mDrinkDetails.drinks[0].strIngredient15,
                    mDrinkDetails.drinks[0].strMeasure15,
                    "15",
                    mDrinkDetails.drinks[0].isShopping15
                )
            )
        }

        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter() {
        mIngredientsAdapter = DrinkDetailsAdapter(this, ::onItemClicked)
        mIngredientsAdapter.differ.submitList(mShoppingIngredientsList)
        binding.rvInstructionsDrinkDetails.adapter = mIngredientsAdapter
        binding.rvInstructionsDrinkDetails.layoutManager = LinearLayoutManager(activity)
    }

    private fun onItemClicked(shoppingItem: ShoppingItem) {
        viewModel.addToShopIconClicked(mDrinkDetails, shoppingItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}