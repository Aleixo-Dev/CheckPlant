package com.nicolas.checkplant.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.nicolas.checkplant.R
import androidx.navigation.fragment.findNavController
import com.nicolas.checkplant.common.AdapterPlant
import com.nicolas.checkplant.databinding.HomeFragmentBinding
import com.nicolas.checkplant.data.model.Plant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        fetchPlantsOfDatabase()
        changeColorToolbar(ContextCompat.getColor(requireContext(), R.color.green))
    }

    private fun changeColorToolbar(color: Int) {
        val window: Window = requireActivity().window
        window.statusBarColor = color
    }


    private fun fetchPlantsOfDatabase() = binding.apply {
        viewModel.plants.observe(viewLifecycleOwner) {
            checkVisibilityPlants(it)
        }
    }

    private fun setupListeners() = binding.apply {
        floatingAddPlant.setOnClickListener {
            findNavController().run {
                navigate(R.id.action_homeFragment_to_addPlantFragment)
            }
        }
    }

    private fun initRecyclerListPlants(listPlants: List<Plant>) = binding.apply {
        with(recyclerPlants) {
            setHasFixedSize(true)
            adapter = AdapterPlant(listPlants) {
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
                findNavController().navigate(directions)
            }
        }
    }

    private fun checkVisibilityPlants(plants: List<Plant>) = binding.apply {
        if (plants.isNotEmpty()) {
            initRecyclerListPlants(plants)
            include.notPlantContainer.visibility = View.GONE
            recyclerPlants.visibility = View.VISIBLE
        } else {
            include.notPlantContainer.visibility = View.VISIBLE
            recyclerPlants.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}