package com.bogdan801.bulletpower.presentation.screens.bullets

import com.bogdan801.bulletpower.domain.model.Bullet

data class BulletsScreenState(
    val searchQuery: String = "",
    val items: List<Bullet> = listOf(),
    val foundItems: List<Bullet> = listOf()
)
