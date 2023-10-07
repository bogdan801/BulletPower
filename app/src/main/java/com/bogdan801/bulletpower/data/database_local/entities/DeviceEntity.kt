package com.bogdan801.bulletpower.data.database_local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan801.bulletpower.domain.model.Device

@Entity
data class DeviceEntity(
    @PrimaryKey(autoGenerate = true)
    val deviceID: Int = 0,
    val name: String,
    val type: String,
    val caliber: Double
){
    fun toDevice(): Device = Device(
        deviceID = deviceID,
        name = name,
        type = type,
        caliber = caliber
    )
}
