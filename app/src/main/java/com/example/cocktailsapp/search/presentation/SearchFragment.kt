package com.example.cocktailsapp.search.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentSearchBinding
import com.example.cocktailsapp.shared.Constants.MY_SEARCH
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var mSearchAdapter: SearchAdapter

    private var searchTerm: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewMissions()

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateSearch.collect { drinks ->
                    when (drinks) {
                        is SearchViewState.ContentSearch -> {
                            binding.loader.visibility = View.GONE
                            drinks.drinks.drinks.sortBy { it.strDrink }
                            mSearchAdapter.differ.submitList(drinks.drinks.drinks)
                            binding.tvCountSearchFragment.text = (drinks.drinks.drinks.size ?: 0).toString() +
                                    " " + resources.getString(R.string.cocktails)
                        }
                        is SearchViewState.Error -> {
                            binding.loader.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                resources.getString(R.string.cocktail_not_found),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is SearchViewState.Loading -> {
                            binding.loader.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        Glide.with(this)
            .load(R.drawable.cocktails_search)
            .into(binding.ivSearchFragment)

    }

    private fun setupRecyclerViewAdapter() {
        mSearchAdapter = SearchAdapter(this)
        binding.rvSearchFragment.layoutManager = LinearLayoutManager(activity)
        binding.rvSearchFragment.adapter = mSearchAdapter
    }

    private fun searchViewMissions() {
        binding.svSearchFragment.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchDrinks(query!!)
                binding.svSearchFragment.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!requireArguments().isEmpty) {
            arguments?.let {
                searchTerm = it.getString(MY_SEARCH)!!
            }
            binding.svSearchFragment.setQuery(searchTerm, true)
        } else {
            binding.svSearchFragment.setQuery(binding.svSearchFragment.query.toString(), true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        arguments?.clear()
        _binding = null
    }
}