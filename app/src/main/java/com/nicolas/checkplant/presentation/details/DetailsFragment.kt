package com.nicolas.checkplant.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nicolas.checkplant.R
import com.nicolas.checkplant.common.AdapterProgressPlant
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    private val arguments: DetailsFragmentArgs by navArgs()

    private var isImageBackground = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupListeners()
        fetchImageFromDatabase()
        setImageIntoBackground()
        checkImageBackground()
    }

    private fun checkImageBackground() = binding.apply {
        if (isImageBackground) {
            imgAdd.visibility = View.GONE
        }
    }

    private fun setImageIntoBackground() = binding.apply {
        arguments.run {
            imgBackgroundPlant.setImageURI(plant.backgroundImage.toUri())
        }
        isImageBackground = true
    }

    private fun fetchImageFromDatabase() = binding.apply {
        viewModel.getImagesPlant(arguments.plant.plantId!!.toInt())
        viewModel.imagesPlant.observe(viewLifecycleOwner) {
            initRecyclerView(it)
        }
    }

    private fun initRecyclerView(imagePlant: List<ImagePlant>) = binding.apply {
        with(rvImages) {
            setHasFixedSize(true)
            adapter = AdapterProgressPlant(imagePlant)
        }
    }

    private fun setupListeners() = binding.apply {
        fabProgress.setOnClickListener {
            val directions =
                DetailsFragmentDirections.actionDetailsFragmentToAddProgressFragment(arguments.plant)
            findNavController().navigate(directions)
        }
    }

    private fun setupToolbar() = binding.apply {
        detailsToolbar.apply {
            tvToolbarName.text = getString(R.string.toolbar_details)
            imgArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}