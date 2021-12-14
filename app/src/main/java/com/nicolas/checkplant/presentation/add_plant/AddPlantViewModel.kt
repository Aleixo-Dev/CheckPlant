package com.nicolas.checkplant.presentation.add_plant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.checkplant.R
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.domain.use_case.AddPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val addPlantUseCase: AddPlantUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorResId = MutableLiveData<Int>()
    val descriptionFieldErrorResId: LiveData<Int?> = _descriptionFieldErrorResId

    private var isFormValid = false

    fun insertPlantIntoDatabase(plant: Plant) = viewModelScope.launch {
        addPlantUseCase.invoke(
            Plant(
                name = plant.name,
                description = plant.description,
                backgroundImage = plant.backgroundImage,
                month = plant.month,
                day = plant.day,
            )
        )
    }

    private fun getErrorStringResIdIfEmpty(value: String): Int? {
        return if (value.isEmpty()) {
            isFormValid = false
            R.string.app_name
        } else null
    }
}