package com.nicolas.checkplant.domain.usecase

import com.nicolas.checkplant.domain.model.Plant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class AddPlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    suspend operator fun invoke(plant: Plant) {
        repository.insertPlant(plant)
    }
}