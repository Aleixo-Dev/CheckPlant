package com.nicolas.checkplant.presentation.add_progress

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nicolas.checkplant.R
import com.nicolas.checkplant.databinding.AddProgressFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.nicolas.checkplant.common.CustomTakePicture
import java.io.File

@AndroidEntryPoint
class AddProgressFragment : BottomSheetDialogFragment() {

    private val arguments: AddProgressFragmentArgs by navArgs()

    private var imageUri: Uri? = null

    private val launcherImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            setImage(it.toString())
        }

    private val launcherImageFromCamera =
        registerForActivityResult(CustomTakePicture()) {}

    private val galleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromGallery.launch("image/*")
                setImage(imageUri.toString())
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromCamera.launch(imageUri)
                setImage(imageUri.toString())
            }
        }

    private var _binding: AddProgressFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddProgressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddProgressFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        binding.imgProgress.setBackgroundResource(R.drawable.background_plant_text)
        binding.imgProgress.setImageResource(R.drawable.img_background_resource)
        observeViewModelEvents()
    }

    private fun setupListeners() = binding.apply {
        imgProgress.setOnClickListener {
            showDialog()
        }

        buttonAddProgress.setOnClickListener {
            val day = inputProgressDay.editText?.text.toString()
            val month = inputProgressMonth.editText?.text.toString()
            viewModel.createProgressPlant(
                imageUri = imageUri,
                plantId = arguments.plant.plantId!!.toLong(),
                day = day,
                month = month
            )
        }
    }

    private fun getRandomFilepath(
        context: Context,
        extension: String,
        directory: String = Environment.DIRECTORY_PICTURES
    ): String {
        return "${
            context.getExternalFilesDir(directory)?.absolutePath
        }/${
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
        galleryPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun openCamera() {
        imageUri = activity?.let {
            getRandomUri(it.applicationContext, ".jpg")
        }
        cameraPermission.launch(Manifest.permission.CAMERA)
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

    private fun setImage(uriImage: String) = binding.apply {
        if (uriImage.isNotEmpty()) {
            Glide.with(imgProgress.context)
                .load(uriImage)
                .centerCrop()
                .into(imgProgress)
        }
    }

    private fun observeViewModelEvents() {
        viewModel.run {
            imageUriErrorResId.observe(viewLifecycleOwner) { drawableResId ->
                Log.d("IMG", drawableResId.toString())
                binding.imgProgress.setImageResource(drawableResId)
            }
            dayFieldErrorResId.observe(viewLifecycleOwner) { dayResId ->
                binding.inputProgressDay.setError(dayResId)
            }
            monthFieldErrorResId.observe(viewLifecycleOwner) { monthResId ->
                binding.inputProgressMonth.setError(monthResId)
            }
        }
    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}