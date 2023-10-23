package com.bogdan801.bulletpower.data.repository

import android.content.Context
import com.bogdan801.bulletpower.data.database_local.Dao
import com.bogdan801.bulletpower.data.database_local.toBulletEntity
import com.bogdan801.bulletpower.data.database_local.toDeviceEntity
import com.bogdan801.bulletpower.data.database_local.toMultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.toShotEntity
import com.bogdan801.bulletpower.data.database_local.toSingleShotRatingEntity
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem
import com.bogdan801.bulletpower.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val dao: Dao
) : Repository {
    override suspend fun insertDevice(device: Device): Long =
        dao.insertDeviceEntity(device.toDeviceEntity())

    override suspend fun updateDevice(device: Device) {
        dao.updateDeviceEntity(device.toDeviceEntity())
    }

    override suspend fun insertBullet(bullet: Bullet): Long =
        dao.insertBulletEntity(bullet.toBulletEntity())

    override suspend fun updateBullet(bullet: Bullet) {
        dao.updateBulletEntity(bullet.toBulletEntity())
    }

    override suspend fun insertSingleShotRatingItem(
        singleShotRatingItem: SingleShotRatingItem,
        deviceID: Int,
        bulletID: Int
    ) = dao.insertSingleShotEntity(singleShotRatingItem.toSingleShotRatingEntity(deviceID, bulletID))

    override suspend fun updateSingleShotRatingItem(
        singleShotRatingItem: SingleShotRatingItem,
        deviceID: Int,
        bulletID: Int
    ){
        val id = isSingleShotItemPresent(singleShotRatingItem)
        dao.updateSingleShotEntity(
            singleShotRatingItem
                .copy(singleShotID = if(id != -1) id else 0)
                .toSingleShotRatingEntity(
                    deviceID = deviceID,
                    bulletID = bulletID
                )
        )
    }

    override suspend fun insertMultipleShotRatingItem(
        multipleShotRatingItem: MultipleShotRatingItem,
        deviceID: Int,
        bulletID: Int
    ): Long {
        val prevID = isMultipleShotItemPresent(multipleShotRatingItem)
        if(prevID != -1) deleteMultipleShotRatingItem(prevID)

        val id = dao.insertMultipleShotEntity(multipleShotRatingItem.toMultipleShotRatingEntity(deviceID, bulletID))
        multipleShotRatingItem.shots.forEach { shotRatingItem ->
            insertShotRatingItem(shotRatingItem.copy(multipleShotID = id.toInt()))
        }

        return id
    }

    override suspend fun insertShotRatingItem(shotRatingItem: ShotRatingItem): Long {
        val id = dao.insertShotEntity(shotRatingItem.toShotEntity())

        val multipleShotRatingEntity = dao.getMultipleShotRatingEntityByID(shotRatingItem.multipleShotID)
        if(multipleShotRatingEntity.shots.isNotEmpty()){
            val averageSpeed = multipleShotRatingEntity.shots.sumOf { it.speed } / multipleShotRatingEntity.shots.size
            val averageEnergy = multipleShotRatingEntity.shots.sumOf { it.energy } / multipleShotRatingEntity.shots.size
            dao.updateMultipleShotEntity(
                multipleShotRatingEntity.multipleShotRating.copy(
                    averageSpeed = averageSpeed,
                    averageEnergy = averageEnergy
                )
            )
        }
        else {
            dao.updateMultipleShotEntity(
                multipleShotRatingEntity.multipleShotRating.copy(
                    averageSpeed = shotRatingItem.speed,
                    averageEnergy = shotRatingItem.energy
                )
            )
        }
        return id
    }
    override suspend fun deleteDevice(deviceID: Int) {
        dao.deleteDevice(deviceID)
    }

    override suspend fun deleteBullet(bulletID: Int) {
        dao.deleteBullet(bulletID)
    }

    override suspend fun deleteSingleShotRatingItem(singleShotID: Int) {
        dao.deleteSingleShotRating(singleShotID)
    }

    override suspend fun deleteMultipleShotRatingItem(multipleShotID: Int) {
        dao.deleteMultipleShotRating(multipleShotID)
    }

    override suspend fun deleteShotRatingItem(shotID: Int) {
        val shot = dao.getShotByID(shotID)
        dao.deleteShot(shotID)
        val multipleShotRatingEntity = dao.getMultipleShotRatingEntityByID(shot.multipleShotID)
        if(multipleShotRatingEntity.shots.isNotEmpty()){
            val averageSpeed = multipleShotRatingEntity.shots.sumOf { it.speed } / multipleShotRatingEntity.shots.size
            val averageEnergy = multipleShotRatingEntity.shots.sumOf { it.energy } / multipleShotRatingEntity.shots.size
            dao.updateMultipleShotEntity(
                multipleShotRatingEntity.multipleShotRating.copy(
                    averageSpeed = averageSpeed,
                    averageEnergy = averageEnergy
                )
            )
        }
        else {
            dao.updateMultipleShotEntity(
                multipleShotRatingEntity.multipleShotRating.copy(
                    averageSpeed = 0.0,
                    averageEnergy = 0.0
                )
            )
        }

    }

    override fun getBullets(): Flow<List<Bullet>> =
        dao.getBullets().map {
            it.map { entity ->
                entity.toBullet()
            }
        }


    override fun getDevices(): Flow<List<Device>> =
        dao.getDevices().map {
            it.map { entity ->
                entity.toDevice()
            }
        }

    override fun getSingleShotRating(): Flow<List<SingleShotRatingItem>> =
        dao.getSingleShotRating().map {
            it.map { entity ->
                val device = dao.getDeviceByID(entity.deviceID).toDevice()
                val bullet = dao.getBulletByID(entity.bulletID).toBullet()

                entity.toSingleShotRatingItem(device, bullet)
            }
        }


    override fun getMultipleShotRating(): Flow<List<MultipleShotRatingItem>> =
        dao.getMultipleShotRating().map {
            it.map { entity ->
                val device = dao.getDeviceByID(entity.multipleShotRating.deviceID).toDevice()
                val bullet = dao.getBulletByID(entity.multipleShotRating.bulletID).toBullet()

                entity.toMultipleShotRatingItem(device, bullet)
            }
        }

    override suspend fun searchBullets(searchQuery: String): List<Bullet> =
        dao.searchBullets(searchQuery).map { it.toBullet() }

    override suspend fun searchDevices(searchQuery: String): List<Device> =
        dao.searchDevices(searchQuery).map { it.toDevice() }

    override fun searchSingleShotRating(searchQuery: String, listToSearch: List<SingleShotRatingItem>): List<SingleShotRatingItem> {
        val outputList = mutableListOf<SingleShotRatingItem>()

        listToSearch.forEach { item ->
            if(item.device!=null && item.bullet!= null){
                if(
                    item.device.name.contains(searchQuery) ||
                    item.bullet.name.contains(searchQuery) ||
                    item.device.caliber.toString().contains(searchQuery) ||
                    item.bullet.caliber.toString().contains(searchQuery) ||
                    item.device.type.contains(searchQuery) ||
                    item.bullet.weight.toString().contains(searchQuery)
                ) {
                    outputList.add(item)
                }
            }
        }

        return outputList
    }

    override fun searchMultipleShotRating(searchQuery: String, listToSearch: List<MultipleShotRatingItem>): List<MultipleShotRatingItem> {
        val outputList = mutableListOf<MultipleShotRatingItem>()

        listToSearch.forEach { item ->
            if(item.device!=null && item.bullet!= null){
                if(
                    item.device.name.contains(searchQuery) ||
                    item.bullet.name.contains(searchQuery) ||
                    item.device.caliber.toString().contains(searchQuery) ||
                    item.bullet.caliber.toString().contains(searchQuery) ||
                    item.device.type.contains(searchQuery) ||
                    item.bullet.weight.toString().contains(searchQuery)
                ) {
                    outputList.add(item)
                }
            }
        }

        return outputList
    }


    //UTIL
    override suspend fun isSingleShotItemPresent(shot: SingleShotRatingItem): Int {
        val rating = getSingleShotRating().first()
        val found = rating.find { it.bullet == shot.bullet && it.device == shot.device }
        return found?.singleShotID ?: -1
    }

    override suspend fun isMultipleShotItemPresent(shot: MultipleShotRatingItem): Int {
        val rating = getMultipleShotRating().first()
        val found = rating.find { it.bullet == shot.bullet && it.device == shot.device }
        return found?.multipleShotID ?: -1
    }
}