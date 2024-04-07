package com.example.cocktailsapp.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailsapp.databinding.FragmentIngredientBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class IngredientFragment : Fragment() {

    private var _binding: FragmentIngredientBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: IngredientListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateIngredient.collect { ingredientsList ->
                    when(ingredientsList) {
                        is HomeViewState.ContentIngredient -> {
                            binding.loader.visibility = View.GONE
                            adapter.differ.submitList(ingredientsList.ingredients.drinks)
                        }
                        is HomeViewState.Error -> {
                            binding.loader.visibility = View.GONE
                        }
                        is HomeViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                        else -> {
                            binding.loader.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerViewAdapter() {
        adapter = IngredientListAdapter(this@IngredientFragment)
        binding.rvIngredientFragment.adapter = adapter
        binding.rvIngredientFragment.layoutManager = GridLayoutManager(activity, 3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}