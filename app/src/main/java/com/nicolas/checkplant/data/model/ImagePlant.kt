package com.nicolas.checkplant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImagePlant(
    @PrimaryKey(autoGenerate = true) val imageId: Long? = null,
    val imageUri: String,
    val imagePlantId: Long,
    val day : String,
    val month : String
)