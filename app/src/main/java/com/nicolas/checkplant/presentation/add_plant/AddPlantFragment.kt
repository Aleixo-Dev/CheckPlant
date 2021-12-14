package com.nicolas.checkplant.presentation.add_plant

import android.app.Dialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
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

@AndroidEntryPoint
class AddPlantFragment : Fragment() {

    private var uriImage: Uri? = null
    private var bitmapImage: Bitmap? = null

    private val launcherImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
            binding.apply {
                uriImage = galleryUri
                showUriIntoImageView(galleryUri)
            }
        }

    private val launcherImageFromCamera =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { cameraBitmap ->
            binding.apply {
                bitmapImage = cameraBitmap
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
            //getImageFromGallery()
            showDialog()
        }
        buttonAddPlant.setOnClickListener {
            if (validateInputTexts()) {
                addPlant()
            }
        }
    }

    private fun addPlant() = binding.apply {
        viewModel.insertPlantIntoDatabase(
            Plant(
                name = inputPlantName.text.toString(),
                description = inputDescriptionPlant.text.toString(),
                month = inputMonth.text.toString(),
                day = inputYear.text.toString(),
                backgroundImage = uriImage.toString()
            )
        )
    }

    private fun validateInputTexts(): Boolean {
        binding.apply {
            if (inputPlantName.text.isNullOrEmpty().not()) {
                if (inputDescriptionPlant.text.isNullOrEmpty().not()) {
                    if (inputMonth.text.isNullOrEmpty().not() && inputYear.text.isNullOrEmpty()
                            .not()
                    ) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun getImageFromGallery() = binding.apply {
        launcherImageFromGallery.launch("image/*")
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

    private fun showUriIntoImageView(uriImage: Uri) = binding.apply {
        Glide.with(addImgPlant.context)
            .load(uriImage)
            .circleCrop()
            .into(addImgPlant)
    }

    private fun showDialog() {
        Dialog(requireContext()).run {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(R.layout.custom_dialog)
            findViewById<TextView>(R.id.tvDialog).text = getString(R.string.dialog_text)
            findViewById<Button>(R.id.buttonCamera).setOnClickListener {
                /**
                 * Open Camera.
                 */
            }
            findViewById<Button>(R.id.buttonGallery).setOnClickListener {
                /**
                 * Open Gallery.
                 */
            }
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}