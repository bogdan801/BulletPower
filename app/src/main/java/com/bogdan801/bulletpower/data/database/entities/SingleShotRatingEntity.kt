package com.bogdan801.bulletpower.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
)
