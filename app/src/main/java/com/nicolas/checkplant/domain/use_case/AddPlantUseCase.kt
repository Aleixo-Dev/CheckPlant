package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.Plant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class AddPlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    suspend operator fun invoke(plant: Plant) {
        repository.insertPlant(plant)
    }
}