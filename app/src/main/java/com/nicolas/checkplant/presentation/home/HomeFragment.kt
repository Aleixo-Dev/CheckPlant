package com.nicolas.checkplant.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nicolas.checkplant.R
import androidx.navigation.fragment.findNavController
import com.nicolas.checkplant.databinding.HomeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : HomeViewModel by viewModels()

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
    }

    private fun setupListeners() = binding.apply {
        floatingAddPlant.setOnClickListener {
            findNavController().run {
                navigate(R.id.action_homeFragment_to_addPlantFragment)
            }
        }
    }

    //Check if home list contain plants, else set visibility automatic.
    // Check visibility, if contain plant set visibility Gone, else set visibility Visible.
    private fun isPlantNotEmpty(isPlant: Boolean = false) = binding.apply {
        if (isPlant) {
            include.notPlantContainer.visibility = View.GONE
        } else {
            include.notPlantContainer.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}