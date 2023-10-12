package com.bogdan801.bulletpower.domain.model

data class Device(
    val deviceID: Int = 0,
    val name: String,
    val type: String,
    val caliber: Double
){
    override fun equals(other: Any?): Boolean {
        val second = other as Device
        return this.name == second.name && this.type == second.type && this.caliber == second.caliber
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + caliber.hashCode()
        return result
    }
}
