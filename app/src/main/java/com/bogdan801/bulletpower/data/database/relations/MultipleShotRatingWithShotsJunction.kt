package com.bogdan801.bulletpower.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.bogdan801.bulletpower.data.database.entities.BulletEntity
import com.bogdan801.bulletpower.data.database.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database.entities.ShotEntity

data class MultipleShotRatingWithShotsJunction(
    @Embedded
    val multipleShotRating: MultipleShotRatingEntity,
    @Relation(
        parentColumn = "multipleShotID",
        entityColumn = "multipleShotID"
    )
    val shots: List<ShotEntity>,
    @Relation(
        parentColumn = "bulletID",
        entityColumn = "bulletID"
    )
    val bullet: BulletEntity,
    @Relation(
        parentColumn = "deviceID",
        entityColumn = "deviceID"
    )
    val device: DeviceEntity
)