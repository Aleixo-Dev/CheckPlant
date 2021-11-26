package com.nicolas.checkplant.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.domain.usecase.GetPlantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlantUseCase: GetPlantUseCase
) : ViewModel() {

    private val _plants = MutableLiveData<List<Plant>>()
    val plants: LiveData<List<Plant>> get() = _plants

    init {
        fetchListPlants()
    }

    private fun fetchListPlants() {
        getPlantUseCase().onEach { listPlants ->
            _plants.value = listPlants
        }.launchIn(viewModelScope)
    }
}