package com.nicolas.checkplant.presentation.add_progress

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.checkplant.R
import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.data.model.PlantName
import com.nicolas.checkplant.domain.use_case.AddImageUseCase
import com.nicolas.checkplant.domain.use_case.PlantNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProgressViewModel @Inject constructor(
    private val addImageUseCase: AddImageUseCase,
    private val plantNameUseCase: PlantNameUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _monthFieldErrorResId = MutableLiveData<Int?>()
    val monthFieldErrorResId: LiveData<Int?> = _monthFieldErrorResId

    private val _dayFieldErrorResId = MutableLiveData<Int?>()
    val dayFieldErrorResId: LiveData<Int?> = _dayFieldErrorResId

    private var isFormValid = false

    fun addPlantName(plantName: PlantName) = viewModelScope.launch {
        plantNameUseCase.invoke(
            PlantName(
                name = plantName.name
            )
        )
    }

    fun addImage(imagePlant: ImagePlant) = viewModelScope.launch {
        addImageUseCase.invoke(
            ImagePlant(
                imageUri = imagePlant.imageUri,
                imagePlantId = imagePlant.imagePlantId,
                day = imagePlant.day,
                month = imagePlant.month
            )
        )
    }

    fun createProgressPlant(
        imageUri: Uri?,
        day: String,
        month: String,
        plantId: Long
    ) = viewModelScope.launch {
        isFormValid = true

        _imageUriErrorResId.value = getDrawableResIdIfNull(imageUri)
        _dayFieldErrorResId.value = getErrorStringResIdIfEmpty(day)
        _monthFieldErrorResId.value = getErrorStringResIdIfEmpty(month)

        if (isFormValid) {
            try {
                addImageUseCase.invoke(
                    ImagePlant(
                        imageUri = imageUri.toString(),
                        imagePlantId = plantId,
                        day = day.toInt(),
                        month = month
                    )
                )
            } catch (exception: Exception) {

            }
        }
    }

    private fun getErrorStringResIdIfEmpty(value: String): Int? {
        return if (value.isEmpty()) {
            isFormValid = false
            R.string.add_plant_field_error
        } else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int {
        return if (value == null) {
            isFormValid = false
            R.drawable.img_background_resource
        } else R.drawable.img_background_resource
    }
}