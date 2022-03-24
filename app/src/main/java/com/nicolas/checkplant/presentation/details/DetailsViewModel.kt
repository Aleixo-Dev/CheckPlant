package com.nicolas.checkplant.presentation.details

import com.nicolas.checkplant.data.model.ImagePlant
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.checkplant.data.model.PlantName
import com.nicolas.checkplant.domain.use_case.DeleteImagePlantUseCase
import com.nicolas.checkplant.domain.use_case.GetImagesPlantUseCase
import com.nicolas.checkplant.domain.use_case.PlantNameFetchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getImagesPlantUseCase: GetImagesPlantUseCase,
    private val deleteImagePlantUseCase: DeleteImagePlantUseCase
) : ViewModel() {

    private val _imagePlantList = MutableLiveData<List<ImagePlant>>()
    val imagesPlant: LiveData<List<ImagePlant>> get() = _imagePlantList

    fun getImagesPlant(id: Int) {
        getImagesPlantUseCase(id).onEach { plants ->
            _imagePlantList.value = plants
        }.launchIn(viewModelScope)
    }

    fun deleteThisImagePlant(imagePlant: ImagePlant) = viewModelScope.launch {
        deleteImagePlantUseCase.invoke(imagePlant)
    }
}