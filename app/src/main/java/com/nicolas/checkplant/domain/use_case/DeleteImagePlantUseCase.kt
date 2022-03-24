package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class DeleteImagePlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    suspend operator fun invoke(imagePlant: ImagePlant) {
        repository.deleteImagePlant(imagePlant)
    }
}