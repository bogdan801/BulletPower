package com.bogdan801.bulletpower.data.database_local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
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
data class SingleShotRatingEntity(
    @PrimaryKey(autoGenerate = true)
    val singleShotID: Int,
    val deviceID: Int,
    val bulletID: Int,
    val speed: Double,
    val energy: Double
){
    fun toSingleShotRatingItem(device: Device, bullet: Bullet) = SingleShotRatingItem(
        singleShotID = singleShotID,
        device = device,
        bullet = bullet,
        speed = speed,
        energy = energy
    )
}
