package com.nicolas.checkplant.presentation.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nicolas.checkplant.R
import com.nicolas.checkplant.common.AdapterProgressPlant
import com.nicolas.checkplant.common.CustomTakePicture
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var uriImage: Uri? = null

    private val launcherImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
            binding.apply {
                uriImage = galleryUri
            }
        }

    private val launcherImageFromCamera =
        registerForActivityResult(CustomTakePicture()) {}

    private val galleryPermissions =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromGallery.launch("image/*")
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromCamera.launch(uriImage)
            }
        }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    private val arguments: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(arguments.plant.name)
        setupListeners()
        fetchImageFromDatabase()
        changeColorToolbar(ContextCompat.getColor(requireContext(), R.color.light_green))
    }

    private fun fetchImageFromDatabase() = binding.apply {
        viewModel.getImagesPlant(arguments.plant.plantId!!.toInt())
        viewModel.imagesPlant.observe(viewLifecycleOwner) {
            initRecyclerView(it)
        }
    }

    private fun changeColorToolbar(color: Int) {
        val window: Window = requireActivity().window
        window.statusBarColor = color
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

    private fun setupToolbar(titlePlant: String) = binding.apply {
        detailsToolbar.apply {
            tvToolbarName.text = titlePlant
            imgArrowBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun showDialog() {
        Dialog(requireContext()).run {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.custom_dialog)
            findViewById<TextView>(R.id.tvDialog).text = getString(R.string.dialog_text)
            findViewById<Button>(R.id.buttonCamera).setOnClickListener {
                dismiss()
                openCamera()
            }
            findViewById<Button>(R.id.buttonGallery).setOnClickListener {
                dismiss()
                openGallery()
            }
            show()
        }
    }

    private fun getRandomFilepath(
        context: Context,
        extension: String,
        directory: String = Environment.DIRECTORY_PICTURES
    ): String {
        return "${context.getExternalFilesDir(directory)?.absolutePath}/${
            System.currentTimeMillis()
        }.$extension"
    }

    private fun getRandomUri(
        context: Context,
        extension: String,
        directory: String = Environment.DIRECTORY_PICTURES
    ): Uri {
        return getUriFromPath(context, getRandomFilepath(context, extension, directory))
    }

    private fun getUriFromPath(context: Context, path: String): Uri {
        return FileProvider.getUriForFile(
            context,
            getContext()?.packageName + ".fileprovider",
            File(path)
        )
    }

    private fun openGallery() {
        galleryPermissions.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun openCamera() {
        uriImage = activity?.let {
            getRandomUri(it.applicationContext, ".jpg")
        }
        cameraPermission.launch(android.Manifest.permission.CAMERA)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}