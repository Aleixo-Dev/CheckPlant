package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class AddImageUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    suspend operator fun invoke(imagePlant: ImagePlant) {
        repository.insertImage(imagePlant)
    }
}