package com.bogdan801.bulletpower.data.database_local

import com.bogdan801.bulletpower.data.database_local.entities.BulletEntity
import com.bogdan801.bulletpower.data.database_local.entities.DeviceEntity
import com.bogdan801.bulletpower.data.database_local.entities.MultipleShotRatingEntity
import com.bogdan801.bulletpower.data.database_local.entities.ShotEntity
import com.bogdan801.bulletpower.data.database_local.entities.SingleShotRatingEntity
import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.MultipleShotRatingItem
import com.bogdan801.bulletpower.domain.model.ShotRatingItem
import com.bogdan801.bulletpower.domain.model.SingleShotRatingItem

fun Bullet.toBulletEntity(): BulletEntity = BulletEntity(
    bulletID = bulletID,
    name = name,
    weight = weight,
    caliber = caliber
)

fun Device.toDeviceEntity(): DeviceEntity = DeviceEntity(
    deviceID = deviceID,
    name = name,
    type = type,
    caliber = caliber
)

fun MultipleShotRatingItem.toMultipleShotRatingEntity(deviceID: Int, bulletID: Int): MultipleShotRatingEntity = MultipleShotRatingEntity(
    multipleShotID = multipleShotID,
    deviceID = deviceID,
    bulletID = bulletID,
    averageSpeed = averageSpeed,
    averageEnergy = averageEnergy
)

fun ShotRatingItem.toShotEntity(): ShotEntity = ShotEntity(
    shotID = shotID,
    multipleShotID = multipleShotID,
    speed = speed,
    energy = energy
)

fun SingleShotRatingItem.toSingleShotRatingEntity(deviceID: Int, bulletID: Int): SingleShotRatingEntity = SingleShotRatingEntity(
    singleShotID = singleShotID,
    deviceID = deviceID,
    bulletID = bulletID,
    speed = speed,
    energy = energy
)

fun ShotRatingItem.toShotEntity(parentID: Int? = null): ShotEntity = ShotEntity(
    shotID = shotID,
    multipleShotID = parentID ?: multipleShotID,
    speed = speed,
    energy = energy
)

