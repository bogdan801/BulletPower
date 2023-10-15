package com.bogdan801.bulletpower.presentation.screens.home

import com.bogdan801.bulletpower.domain.model.Bullet
import com.bogdan801.bulletpower.domain.model.Device
import com.bogdan801.bulletpower.domain.model.ShotRatingItem

data class HomeScreenState(
    val device: Device? = null,
    val bullet: Bullet? = null,
    val bulletWeight: Double = 0.0,
    val singleShotSpeed: Double = 0.0,
    val singleShotEnergy: Double = 0.0,
    val multipleShotSpeed: Double = 0.0,
    val multipleShotEnergy: Double = 0.0,
    val shotSeries: List<ShotRatingItem> = listOf()
)
