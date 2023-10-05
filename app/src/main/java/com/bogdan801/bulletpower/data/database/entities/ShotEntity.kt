package com.bogdan801.bulletpower.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MultipleShotRatingEntity::class,
            parentColumns = ["multipleShotID"],
            childColumns = ["multipleShotID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShotEntity(
    @PrimaryKey(autoGenerate = true)
    val shotID: Int,
    val multipleShotID: Int,
    val energy: Double,
    val speed: Double
)
