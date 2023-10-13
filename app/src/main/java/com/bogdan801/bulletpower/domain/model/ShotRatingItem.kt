package com.bogdan801.bulletpower.domain.model

data class ShotRatingItem(
    val shotID: Int = 0,
    val multipleShotID: Int = 0,
    val speed: Double,
    val energy: Double
)

fun List<ShotRatingItem>.toStringRepresentation(): String{
    val thisList = this
    return buildString {
        thisList.forEachIndexed { index, shot ->
            append("${shot.speed},${shot.energy}")
            if(index != thisList.lastIndex) append(";")
        }
    }
}

fun String.toShotList(): List<ShotRatingItem>{
    val shotStrings = this.split(";")
    val output = mutableListOf<ShotRatingItem>()
    shotStrings.forEach { shotString ->
        val parts = shotString.split(",")
        val speed = parts[0].toDouble()
        val energy = parts[1].toDouble()
        output.add(ShotRatingItem(speed = speed, energy = energy))
    }
    return output
}