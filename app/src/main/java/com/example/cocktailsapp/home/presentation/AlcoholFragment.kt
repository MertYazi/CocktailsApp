package com.example.cocktailsapp.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailsapp.databinding.FragmentAlcoholBinding
import com.example.cocktailsapp.home.business.AlcoholItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlcoholFragment : Fragment() {

    private var _binding: FragmentAlcoholBinding? = null
    private val binding get() = _binding!!

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
        arguments.let {
            val alcoholList = it!!.getSerializable("myAlcoholList") as List<AlcoholItem>
            adapter = AlcoholListAdapter(this@AlcoholFragment)
            adapter.differ.submitList(alcoholList)
            binding.rvAlcoholFragment.adapter = adapter
            binding.rvAlcoholFragment.layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}