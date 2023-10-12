package com.bogdan801.bulletpower.domain.model

data class SingleShotRatingItem(
    val singleShotID: Int = 0,
    val device: Device? = null,
    val bullet: Bullet? = null,
    val speed: Double,
    val energy: Double
){

}