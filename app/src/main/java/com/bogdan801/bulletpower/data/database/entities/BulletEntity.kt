package com.bogdan801.bulletpower.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BulletEntity(
    @PrimaryKey(autoGenerate = true)
    val bulletID: Int,
    val name: String,
    val weight: Double,
    val caliber: String
)