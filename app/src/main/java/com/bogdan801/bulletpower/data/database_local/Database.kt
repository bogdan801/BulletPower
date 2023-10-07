package com.bogdan801.bulletpower.data.database_local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan801.bulletpower.data.database_local.entities.BulletEntity
import com.bogdan801.bulletpower.data.database_local.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database_local.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.entities.ShotEntity
import com.bogdan801.bulletpower.data.database_local.entities.SingleShotRatingEntity

@Database(
    entities = [
        BulletEntity::class,
        DeviceEntity::class,
        MultipleShotRatingEntity::class,
        ShotEntity::class,
        SingleShotRatingEntity::class
    ],
    exportSchema = true,
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract val dbDao: Dao
}