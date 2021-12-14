package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class GetPlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    operator fun invoke() = repository.getPlants()
}