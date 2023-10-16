package com.bogdan801.bulletpower.presentation.screens.devices

import com.bogdan801.bulletpower.domain.model.Device

data class DevicesScreenState(
    val searchQuery: String = "",
    val items: List<Device> = listOf(),
    val foundItems: List<Device> = listOf()
)
