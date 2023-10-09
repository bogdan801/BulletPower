package com.bogdan801.bulletpower.data.database_local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.bogdan801.bulletpower.data.database_local.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.entities.ShotEntity
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem

data class MultipleShotRatingWithShotsJunction(
    @Embedded
    val multipleShotRating: MultipleShotRatingEntity,
    @Relation(
        parentColumn = "multipleShotID",
        entityColumn = "multipleShotID"
    )
    val shots: List<ShotEntity>
){
    fun toMultipleShotRatingItem(device: Device, bullet: Bullet) = MultipleShotRatingItem(
        multipleShotID = multipleShotRating.multipleShotID,
        device = device,
        bullet = bullet,
        averageSpeed = multipleShotRating.averageSpeed,
        averageEnergy = multipleShotRating.averageEnergy,
        shots = shots.map { it.toShotRatingItem() }
    )
}