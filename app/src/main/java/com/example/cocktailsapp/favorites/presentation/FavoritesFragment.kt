package com.example.cocktailsapp.favorites.presentation

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
import com.example.cocktailsapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel  by viewModels()

    private lateinit var mFavoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFavorites.collect { favorites ->
                    when (favorites) {
                        is FavoritesViewState.ContentFavorites -> {
                            binding.loader.visibility = View.GONE
                            favorites.drinks.drinks.sortBy { it.strDrink }
                            mFavoritesAdapter.differ.submitList(favorites.drinks.drinks)
                            binding.tvCountFavoritesFragment.text = (favorites.drinks.drinks.size ?: 0).toString() +
                                    " " + resources.getString(R.string.cocktails)
                        }
                        is FavoritesViewState.Error -> {
                            binding.loader.visibility = View.GONE
                        }
                        is FavoritesViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        viewModel.getFavorites()

        Glide.with(this)
            .load(R.drawable.cocktails_favorites)
            .into(binding.ivFavoritesFragment)

    }

    private fun setupRecyclerViewAdapter() {
        mFavoritesAdapter = FavoritesAdapter(this)
        binding.rvFavoritesFragment.layoutManager = LinearLayoutManager(activity)
        binding.rvFavoritesFragment.adapter = mFavoritesAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}