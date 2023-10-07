package com.bogdan801.bulletpower.data.repository

import android.content.Context
import com.bogdan801.bulletpower.data.database_local.Dao
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val applicationContext: Context,
    private val dao: Dao
) : Repository {
    override suspend fun insertDevice(device: Device): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertBullet(bullet: Bullet): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertSingleShotRatingItem(singleShotRatingItem: SingleShotRatingItem): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertMultipleShotRatingItem(multipleShotRatingItem: MultipleShotRatingItem): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertShotRatingItem(shotRatingItem: ShotRatingItem): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDevice(deviceID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBullet(bulletID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSingleShotRatingItem(singleShotID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMultipleShotRatingItem(multipleShotID: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteShotRatingItem(shotID: Int) {
        TODO("Not yet implemented")
    }

    override fun getBullets(): Flow<List<Bullet>> {
        TODO("Not yet implemented")
    }

    override fun getDevices(): Flow<List<Device>> {
        TODO("Not yet implemented")
    }

    override fun getSingleShotRating(): Flow<List<SingleShotRatingItem>> {
        TODO("Not yet implemented")
    }

    override fun getMultipleShotRating(): Flow<List<MultipleShotRatingItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchBullets(searchQuery: String): List<Bullet> {
        TODO("Not yet implemented")
    }

    override suspend fun searchDevices(searchQuery: String): List<Device> {
        TODO("Not yet implemented")
    }

    override suspend fun searchSingleShotRating(searchQuery: String): List<SingleShotRatingItem> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMultipleShotRating(searchQuery: String): List<MultipleShotRatingItem> {
        TODO("Not yet implemented")
    }

}