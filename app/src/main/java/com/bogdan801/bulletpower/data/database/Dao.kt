package com.bogdan801.bulletpower.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bogdan801.bulletpower.data.database.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database.entities.ShotEntity
import com.bogdan801.bulletpower.data.database.entities.SingleShotRatingEntity

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceEntity(deviceEntity: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBulletEntity(deviceEntity: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleShotEntity(singleShotRatingEntity: SingleShotRatingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleShotEntity(multipleShotRatingEntity: MultipleShotRatingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShotEntity(shotEntity: ShotEntity)
}