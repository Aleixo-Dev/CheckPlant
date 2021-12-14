package com.nicolas.checkplant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_names")
data class PlantName(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name : String
)