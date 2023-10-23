package com.bogdan801.bulletpower.domain.repository

import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertDevice(device: Device): Long
    suspend fun updateDevice(device: Device)
    suspend fun insertBullet(bullet: Bullet): Long
    suspend fun updateBullet(bullet: Bullet)
    suspend fun insertSingleShotRatingItem(singleShotRatingItem: SingleShotRatingItem, deviceID: Int, bulletID: Int): Long
    suspend fun updateSingleShotRatingItem(singleShotRatingItem: SingleShotRatingItem, deviceID: Int, bulletID: Int)
    suspend fun insertMultipleShotRatingItem(multipleShotRatingItem: MultipleShotRatingItem, deviceID: Int, bulletID: Int): Long
    suspend fun insertShotRatingItem(shotRatingItem: ShotRatingItem): Long

    suspend fun deleteDevice(deviceID: Int)
    suspend fun deleteBullet(bulletID: Int)
    suspend fun deleteSingleShotRatingItem(singleShotID: Int)
    suspend fun deleteMultipleShotRatingItem(multipleShotID: Int)
    suspend fun deleteShotRatingItem(shotID: Int)

    fun getBullets(): Flow<List<Bullet>>
    fun getDevices(): Flow<List<Device>>
    fun getSingleShotRating(): Flow<List<SingleShotRatingItem>>
    fun getMultipleShotRating(): Flow<List<MultipleShotRatingItem>>

    suspend fun searchBullets(searchQuery: String): List<Bullet>
    suspend fun searchDevices(searchQuery: String): List<Device>
    fun searchSingleShotRating(searchQuery: String, listToSearch: List<SingleShotRatingItem>): List<SingleShotRatingItem>
    fun searchMultipleShotRating(searchQuery: String, listToSearch: List<MultipleShotRatingItem>): List<MultipleShotRatingItem>

    suspend fun isSingleShotItemPresent(shot: SingleShotRatingItem): Int
    suspend fun isMultipleShotItemPresent(shot: MultipleShotRatingItem): Int
}