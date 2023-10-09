package com.bogdan801.bulletpower.data.database_local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DeviceEntity::class,
            parentColumns = ["deviceID"],
            childColumns = ["deviceID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BulletEntity::class,
            parentColumns = ["bulletID"],
            childColumns = ["bulletID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MultipleShotRatingEntity(
    @PrimaryKey(autoGenerate = true)
    val multipleShotID: Int,
    val deviceID: Int,
    val bulletID: Int,
    val averageSpeed: Double,
    val averageEnergy: Double
){
    fun toMultipleShotRatingItem(device: Device, bullet: Bullet, shots: List<ShotRatingItem>) = MultipleShotRatingItem(
        multipleShotID = multipleShotID,
        device = device,
        bullet = bullet,
        averageSpeed = averageSpeed,
        averageEnergy = averageEnergy,
        shots = shots
    )
}


