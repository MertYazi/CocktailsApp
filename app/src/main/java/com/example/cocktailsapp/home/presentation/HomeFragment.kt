package com.example.cocktailsapp.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cocktailsapp.R
import com.example.cocktailsapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        setupViewPager()

        searchViewDoneAction()

        Glide.with(this)
            .load(R.drawable.cocktails_home)
            .into(binding.ivHomeFragment)
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(CategoryFragment(), resources.getString(R.string.category_title))
        adapter.addFragment(GlassFragment(), resources.getString(R.string.glass_title))
        adapter.addFragment(IngredientFragment(), resources.getString(R.string.ingredient_title))
        adapter.addFragment(AlcoholFragment(), resources.getString(R.string.alcohol_title))
        binding.viewpager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }

    private fun searchViewDoneAction() {
        val searchDone: EditText = binding.svSearch.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        searchDone.setOnEditorActionListener { search, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = search.text.toString()
                binding.svSearch.setQuery("", false)
                binding.svSearch.clearFocus()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                    searchText
                ))
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}