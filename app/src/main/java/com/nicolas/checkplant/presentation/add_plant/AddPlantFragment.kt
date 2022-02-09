package com.nicolas.checkplant.presentation.add_plant

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nicolas.checkplant.R
import com.nicolas.checkplant.databinding.AddPlantFragmentBinding
import com.nicolas.checkplant.data.model.Plant
import dagger.hilt.android.AndroidEntryPoint
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.core.content.FileProvider
import com.nicolas.checkplant.common.CustomTakePicture
import java.io.File

@AndroidEntryPoint
class AddPlantFragment : Fragment() {

    private var uriImage: Uri? = null

    private val launcherImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
            binding.apply {
                uriImage = galleryUri
                showUriIntoImageView(galleryUri.toString())
            }
        }

    private val launcherImageFromCamera =
        registerForActivityResult(CustomTakePicture()) {}

    private val galleryPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromGallery.launch("image/*")
                showUriIntoImageView(uriImage.toString())
            }
        }

    private val cameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                launcherImageFromCamera.launch(uriImage)
                showUriIntoImageView(uriImage.toString())
            }
        }

    private var _binding: AddPlantFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddPlantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddPlantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupListeners()
        binding.addImgPlant.setBackgroundResource(R.drawable.background_plant_text)
        binding.addImgPlant.setImageResource(R.drawable.img_background_resource)
    }

    private fun setupListeners() = binding.apply {
        addImgPlant.setOnClickListener {
            showDialog()
        }
        buttonAddPlant.setOnClickListener {
            if (validateInputTexts()) {
                addPlant()
                findNavController().navigate(R.id.action_addPlantFragment_to_homeFragment)
            }
        }
    }

    private fun addPlant() = binding.apply {
        viewModel.insertPlantIntoDatabase(
            Plant(
                name = textInputPlantName.editText?.text.toString(),
                description = inputDescriptionPlant.text.toString(),
                month = inputMonthAdd.editText?.text.toString(),
                day = inputDayAdd.editText?.text.toString(),
                backgroundImage = uriImage.toString()
            )
        )
    }

    private fun validateInputTexts(): Boolean {
        binding.apply {
            if (textInputPlantName.editText?.text!!.isNotEmpty()) {
                if (inputDescriptionPlant.text.isNullOrEmpty().not()) {
                    if (inputDayAdd.editText?.text!!.isNotEmpty() && inputMonthAdd.editText?.text!!.isNotEmpty()
                    ) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun setupToolbar() = binding.apply {
        this.includeToolbar.apply {
            this.imgArrowBack.setOnClickListener {
                findNavController().run {
                    popBackStack()
                }
            }
            this.tvToolbarName.text = getString(R.string.add_plant)
            this.imgShare.visibility = View.GONE
        }
    }

    private fun showUriIntoImageView(uriImage: String) = binding.apply {
        if (uriImage.isNotEmpty()) {
            Glide.with(addImgPlant.context)
                .load(uriImage)
                .circleCrop()
                .into(addImgPlant)
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

    private fun openGallery() {
        galleryPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun openCamera() {
        uriImage = activity?.let {
            getRandomUri(it.applicationContext, ".jpg")
        }
        cameraPermission.launch(android.Manifest.permission.CAMERA)
    }

    private fun getRandomFilepath(
        context: Context,
        extension: String,
        directory: String = Environment.DIRECTORY_PICTURES
    ): String {
        return "${context.getExternalFilesDir(directory)?.absolutePath}/${System.currentTimeMillis()}.$extension"
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}