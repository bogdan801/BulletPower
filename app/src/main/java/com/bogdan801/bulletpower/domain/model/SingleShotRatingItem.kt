package com.bogdan801.bulletpower.domain.model

data class SingleShotRatingItem(
    val singleShotID: Int,
    val device: Device,
    val bullet: Bullet,
    val speed: Double,
    val energy: Double
)