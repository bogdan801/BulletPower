package com.bogdan801.bulletpower.data.database_local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bogdan801.bulletpower.data.database_local.entities.BulletEntity
import com.bogdan801.bulletpower.data.database_local.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database_local.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.entities.ShotEntity
import com.bogdan801.bulletpower.data.database_local.entities.SingleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.relations.MultipleShotRatingWithShotsJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceEntity(deviceEntity: DeviceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBulletEntity(deviceEntity: DeviceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleShotEntity(singleShotRatingEntity: SingleShotRatingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleShotEntity(multipleShotRatingEntity: MultipleShotRatingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShotEntity(shotEntity: ShotEntity): Long

    //DELETE
    @Query("DELETE FROM deviceentity WHERE deviceID == :deviceID")
    suspend fun deleteDevice(deviceID: Int)

    @Query("DELETE FROM bulletentity WHERE bulletID == :bulletID")
    suspend fun deleteBullet(bulletID: Int)

    @Query("DELETE FROM singleshotratingentity WHERE singleShotID == :singleShotID")
    suspend fun deleteSingleShotRating(singleShotID: Int)

    @Query("DELETE FROM multipleshotratingentity WHERE multipleShotID == :multipleShotID")
    suspend fun deleteMultipleShotRating(multipleShotID: Int)

    @Query("DELETE FROM shotentity WHERE shotID == :shotID")
    suspend fun deleteShot(shotID: Int)

    //SELECT
    @Query("SELECT * FROM deviceentity")
    fun getDevices() : Flow<List<DeviceEntity>>

    @Query("SELECT * FROM bulletentity")
    fun getBullets() : Flow<List<BulletEntity>>

    @Query("SELECT * FROM deviceentity WHERE deviceID = :deviceID")
    suspend fun getDeviceByID(deviceID: Int) : DeviceEntity

    @Query("SELECT * FROM bulletentity WHERE bulletID = :bulletID")
    suspend fun getBulletByID(bulletID: Int) : BulletEntity

    @Query("SELECT * FROM singleshotratingentity")
    fun getSingleShotRating() : Flow<List<SingleShotRatingEntity>>

    @Query("SELECT * FROM multipleshotratingentity")
    fun getMultipleShotRating(): Flow<List<MultipleShotRatingWithShotsJunction>>

    @Query("SELECT * FROM shotentity WHERE shotID = :shotID")
    suspend fun getShotByID(shotID: Int) : ShotEntity

    //SEARCH
    @Query(
        """
            SELECT * 
            FROM deviceentity 
            WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%' OR LOWER(type) LIKE '%' || LOWER(:searchQuery) || '%'
        """
    )
    suspend fun searchDevices(searchQuery: String) : List<DeviceEntity>

    @Query(
        """
            SELECT * 
            FROM bulletentity 
            WHERE LOWER(name) LIKE '%' || LOWER(:searchQuery) || '%' OR LOWER(caliber) LIKE '%' || LOWER(:searchQuery) || '%'
        """
    )
    suspend fun searchBullets(searchQuery: String) : List<BulletEntity>
}