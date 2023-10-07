package com.bogdan801.bulletpower.domain.model

data class MultipleShotRatingItem(
    val multipleShotID: Int,
    val device: Device,
    val bullet: Bullet,
    val averageSpeed: Double,
    val averageEnergy: Double,
)