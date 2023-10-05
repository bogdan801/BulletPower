package com.bogdan801.bulletpower.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    val deviceID: Int,
    val name: String,
    val type: String,
    val caliber: String
)
