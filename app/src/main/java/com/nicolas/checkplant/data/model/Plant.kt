package com.nicolas.checkplant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val description: String,
    val image: String,
    val month: String,
    val year : String
)