package com.bogdan801.bulletpower.domain.model

data class MultipleShotRatingItem(
    val multipleShotID: Int = 0,
    val device: Device? = null,
    val bullet: Bullet? = null,
    val averageSpeed: Double = 0.0,
    val averageEnergy: Double = 0.0,
    val shots: List<ShotRatingItem>
)