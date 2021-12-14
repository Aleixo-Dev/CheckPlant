package com.nicolas.checkplant.data.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey val plantId: Long? = null,
    val name: String,
    val description: String,
    val backgroundImage: String,
    val day: String,
    val month: String
) : Serializable

