package com.nicolas.checkplant.presentation.details

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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

    private var backgroundUri: Uri? = null
    private val arguments: DetailsFragmentArgs by navArgs()

    private val getContentBackground =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            backgroundUri = it
            showUriIntoImageView(it)
            isImage(it)
        }

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
    }


    private fun fetchImageFromDatabase() = binding.apply {
        viewModel.getImagesPlant(arguments.plant.plantId!!.toInt())
        viewModel.imagesPlant.observe(viewLifecycleOwner) {
            initRecyclerView(it)
        }
    }

    private fun isImage(imageUri: Uri) = binding.apply {
        if (imageUri.toString().isNotEmpty()) {
            imgBackgroundPlant.visibility = View.VISIBLE
            imgAdd.visibility = View.GONE
        } else {
            imgBackgroundPlant.visibility = View.GONE
            imgAdd.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView(imagePlant: List<ImagePlant>) = binding.apply {
        with(rvImages) {
            setHasFixedSize(true)
            adapter = AdapterProgressPlant(imagePlant)
        }
    }

    private fun setupListeners() = binding.apply {
        imgBackgroundPlant.setOnClickListener {
            fetchImageFromGallery()
        }
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

    private fun fetchImageFromGallery() = binding.apply {
        getContentBackground.launch("image/*")
    }

    private fun showUriIntoImageView(uriImage: Uri) = binding.apply {

        val options: RequestOptions = RequestOptions()
            .centerInside()
            .placeholder(R.drawable.ic_main_logo_application)
            .error(R.drawable.ic_main_logo_application)
        Glide.with(imgBackgroundPlant.context)
            .load(uriImage)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .apply(options)
            .dontAnimate()
            .into(imgBackgroundPlant)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}