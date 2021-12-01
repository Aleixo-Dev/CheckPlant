package com.nicolas.checkplant.presentation.details

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.nicolas.checkplant.R
import com.nicolas.checkplant.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var backgroundUri: Uri? = null

    private val getContentBackground =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            backgroundUri = it
            showUriIntoImageView(it)
            isImage(it)
        }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

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
    }

    private fun isImage(imageUri : Uri) = binding.apply {
        if(imageUri.toString().isNotEmpty()){
            imgBackgroundPlant.visibility = View.VISIBLE
            imgAdd.visibility = View.GONE
        }else{
            imgBackgroundPlant.visibility = View.GONE
            imgAdd.visibility = View.VISIBLE
        }
    }

    private fun setupListeners() = binding.apply {
        imgBackgroundPlant.setOnClickListener {
            fetchImageFromGallery()
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

        val options : RequestOptions = RequestOptions()
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