package com.bogdan801.bulletpower.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.bogdan801.bulletpower.data.database.entities.BulletEntity
import com.bogdan801.bulletpower.data.database.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database.entities.MultipleShotRatingEntity

data class ExpandedSingleShotRating(
    @Embedded
    val multipleShotRating: MultipleShotRatingEntity,
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
