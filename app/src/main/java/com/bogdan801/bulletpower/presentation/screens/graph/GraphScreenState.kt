package com.bogdan801.bulletpower.presentation.screens.graph

import co.yml.charts.common.model.Point

data class GraphScreenState(
    val dataToShow: GraphDataType = GraphDataType.Energy,
    val speedPoints: List<Point> = listOf(),
    val energyPoints: List<Point> = listOf(),
)

enum class GraphDataType(val title: String){
    Speed("Швидкість, м/с"), Energy("Енергія, Дж")
}
