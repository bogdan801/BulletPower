package com.bogdan801.bulletpower.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bullet (
    val bulletID: Int = 0,
    val name: String,
    val weight: Double,
    val caliber: Double
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        val second = other as Bullet
        return this.name == second.name && this.weight == second.weight && this.caliber == second.caliber
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + caliber.hashCode()
        return result
    }
}