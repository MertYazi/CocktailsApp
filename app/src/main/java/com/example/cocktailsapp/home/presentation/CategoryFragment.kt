package com.example.cocktailsapp.home.presentation

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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailsapp.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateCategory.collect { categoriesList ->
                    when(categoriesList) {
                        is HomeViewState.ContentCategory -> {
                            binding.loader.visibility = View.GONE
                            adapter.differ.submitList(categoriesList.categories.drinks)
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
        adapter = CategoryListAdapter(this@CategoryFragment)
        binding.rvCategoryFragment.adapter = adapter
        binding.rvCategoryFragment.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}