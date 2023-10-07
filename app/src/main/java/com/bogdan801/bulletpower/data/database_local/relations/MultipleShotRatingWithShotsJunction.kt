package com.bogdan801.bulletpower.data.database_local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.bogdan801.bulletpower.data.database_local.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.entities.ShotEntity

data class MultipleShotRatingWithShotsJunction(
    @Embedded
    val multipleShotRating: MultipleShotRatingEntity,
    @Relation(
        parentColumn = "multipleShotID",
        entityColumn = "multipleShotID"
    )
    val shots: List<ShotEntity>
)