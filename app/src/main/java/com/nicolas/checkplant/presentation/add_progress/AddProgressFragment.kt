package com.nicolas.checkplant.presentation.add_progress

import android.net.Uri
import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.nicolas.checkplant.common.showToast
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.PlantName

@AndroidEntryPoint
class AddProgressFragment : BottomSheetDialogFragment() {


    private val arguments: AddProgressFragmentArgs by navArgs()

    /**
     * Get plantId select from home fragment.
     */

    private var _binding: AddProgressFragmentBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        binding.imgProgress.setImageURI(it)
    }

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
    }

    private fun setupListeners() = binding.apply {
        imgProgress.setOnClickListener {
            chooseImage()
        }
        buttonAddProgress.setOnClickListener {
            val day = inputProgressDay.text.toString()
            val month = inputProgressMonth.text.toString()
            viewModel.addImage(
                ImagePlant(
                    imageUri = imageUri.toString(),
                    imagePlantId = arguments.plant.plantId!!.toLong()
                )
            )
        }
    }

    private fun observeViewModelEvents() {
        viewModel.run {
            imageUriErrorResId.observe(viewLifecycleOwner) { drawableResId ->
                Log.d("IMG", drawableResId.toString())
                binding.imgProgress.setImageResource(drawableResId)
            }
            dayFieldErrorResId.observe(viewLifecycleOwner) { dayResId ->
                binding.inputProgressDay.error = dayResId.toString()
            }
            monthFieldErrorResId.observe(viewLifecycleOwner) { monthResId ->
                binding.inputProgressMonth.error = monthResId.toString()
            }
        }
    }

    private fun chooseImage() {
        getContent.launch("image/*")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}