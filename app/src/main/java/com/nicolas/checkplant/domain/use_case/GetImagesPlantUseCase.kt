package com.nicolas.checkplant.domain.use_case

import com.nicolas.checkplant.data.model.ImagePlant
import com.nicolas.checkplant.domain.repository.CheckPlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesPlantUseCase @Inject constructor(
    private val repository: CheckPlantRepository
) {
    operator fun invoke(id : Int): Flow<List<ImagePlant>> {
        return repository.getImagesFromProgress(id)
    }
}