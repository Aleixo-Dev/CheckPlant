package com.nicolas.checkplant.domain.usecase

import com.nicolas.checkplant.domain.model.Plant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    suspend operator fun invoke() : Flow<List<Plant>> {
        return repository.getPlants()
    }
}