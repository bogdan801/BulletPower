package com.bogdan801.bulletpower.presentation.screens.devices

import com.bogdan801.bulletpower.domain.model.Device

data class DevicesScreenState(
    val searchQuery: String = "",
    val items: List<Device> = listOf(
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),
        Device(name = "Diana P5 Magnum", type = "ППП", caliber = 4.5),
        Device(name = "Taurus 409", type = "Флобер", caliber = 4.0),

    ),
    val foundItems: List<Device> = listOf()
)
