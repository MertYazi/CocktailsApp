package com.example.cocktailsapp.search.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.viewStateSearch.observe(viewLifecycleOwner) { drinks ->
            when (drinks) {
                is SearchViewState.ContentSearch -> {
                    binding.loader.visibility = View.GONE
                    mSearchAdapter.differ.submitList(drinks.drinks.drinks)
                    binding.tvCountSearchFragment.text = (drinks.drinks.drinks.size ?: 0).toString() +
                            " " + resources.getString(R.string.cocktails)
                }
                is SearchViewState.Error -> {
                    binding.loader.visibility = View.GONE
                }
                is SearchViewState.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                }
                else -> { }
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

        binding.svSearchFragment.setOnCloseListener {
            viewModel.searchDrinks("")
            false
        }
    }

    override fun onResume() {
        super.onResume()
        if (!requireArguments().isEmpty) {
            val args: SearchFragmentArgs by navArgs()
            searchTerm = args.mySearch
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