package com.bogdan801.bulletpower.data.database_local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bogdan801.bulletpower.domain.model.Bullet

@Entity
data class BulletEntity(
    @PrimaryKey(autoGenerate = true)
    val bulletID: Int,
    val name: String,
    val weight: Double,
    val caliber: Double
){
    fun toBullet(): Bullet = Bullet(
        bulletID = bulletID,
        name = name,
        weight = weight,
        caliber = caliber
    )
}