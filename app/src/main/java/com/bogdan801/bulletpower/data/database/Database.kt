package com.bogdan801.bulletpower.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan801.bulletpower.data.database.entities.BulletEntity
import com.bogdan801.bulletpower.data.database.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database.entities.ShotEntity
import com.bogdan801.bulletpower.data.database.entities.SingleShotRatingEntity

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