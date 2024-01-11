package com.example.cocktailsapp.favorites.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.viewStateFavorites.observe(viewLifecycleOwner) { favorites ->
            when (favorites) {
                is FavoritesViewState.ContentFavorites -> {
                    mFavoritesAdapter = FavoritesAdapter(this)
                    mFavoritesAdapter.differ.submitList(favorites.drinks.drinks)
                    binding.rvFavoritesFragment.layoutManager = LinearLayoutManager(activity)
                    binding.rvFavoritesFragment.adapter = mFavoritesAdapter
                }
                is FavoritesViewState.Error -> {
                    //
                }
                is FavoritesViewState.Loading -> {
                    //
                }
                else -> { }
            }
        }

        Glide.with(this)
            .load(R.drawable.cocktails_background)
            //.override(120, 120)
            .into(binding.ivFavoritesFragment)

        binding.tvFavoritesFragment.text = "Favorites"

        viewModel.getFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}