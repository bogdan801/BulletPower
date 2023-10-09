package com.bogdan801.bulletpower.data.database_local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.bogdan801.bulletpower.domain.model.ShotRatingItem

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
    val shotID: Int = 0,
    val multipleShotID: Int,
    val energy: Double,
    val speed: Double
){
    fun toShotRatingItem() = ShotRatingItem(shotID, multipleShotID, speed, energy)
}
