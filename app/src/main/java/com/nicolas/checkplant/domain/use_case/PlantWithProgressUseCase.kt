package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import javax.inject.Inject

class PlantWithProgressUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {

}