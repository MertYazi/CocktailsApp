package com.example.cocktailsapp.home.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailsapp.databinding.FragmentGlassBinding
import com.example.cocktailsapp.home.business.GlassItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GlassFragment : Fragment() {

    private var _binding: FragmentGlassBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GlassListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
            val glassList = it!!.getSerializable("myGlassList") as List<GlassItem>
            adapter = GlassListAdapter(this@GlassFragment)
            adapter.differ.submitList(glassList)
            binding.rvGlassFragment.adapter = adapter
            binding.rvGlassFragment.layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}