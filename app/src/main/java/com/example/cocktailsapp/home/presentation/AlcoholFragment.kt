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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailsapp.databinding.FragmentAlcoholBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Mert on 2024
 */
@AndroidEntryPoint
class AlcoholFragment : Fragment() {

    private var _binding: FragmentAlcoholBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: AlcoholListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlcoholBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateAlcohol.collect { alcoholsList ->
                    when(alcoholsList) {
                        is HomeViewState.ContentAlcohol -> {
                            binding.loader.visibility = View.GONE
                            adapter.differ.submitList(alcoholsList.alcohols.drinks)
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
        adapter = AlcoholListAdapter(this@AlcoholFragment)
        binding.rvAlcoholFragment.adapter = adapter
        binding.rvAlcoholFragment.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}