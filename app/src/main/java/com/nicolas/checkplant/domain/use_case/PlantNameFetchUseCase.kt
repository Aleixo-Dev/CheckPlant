package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.PlantName
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantNameFetchUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {

    operator fun invoke(): Flow<List<PlantName>> {
        return repository.getAllPlantName()
    }

}