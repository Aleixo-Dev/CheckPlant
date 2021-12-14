package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.PlantName
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class PlantNameUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {

    suspend operator fun invoke(plantName: PlantName){
        repository.insertPlantName(plantName)
    }

}