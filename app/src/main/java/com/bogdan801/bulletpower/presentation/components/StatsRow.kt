package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StatsRow(
    modifier: Modifier = Modifier,
    height: Dp = 36.dp,
    type: StatsRowType = StatsRowType.Titles
) {
    when (type) {
        is StatsRowType.Titles -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                EmptyGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(height)
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = "Мінімум"
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = "Середнє",
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    textStyle = MaterialTheme.typography.displaySmall
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = "Максимум"
                )
            }
        }

        is StatsRowType.Speed -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(height),
                    text = "м/с"
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.1f", type.min),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.1f", type.med),
                    textStyle = MaterialTheme.typography.displaySmall
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.1f", type.max),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }

        is StatsRowType.Energy -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(height),
                    text = "Дж"
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.2f", type.min),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.2f", type.med),
                    textStyle = MaterialTheme.typography.displaySmall
                )
                Spacer(w = 1.dp)
                TextGridCell(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = String.format("%.2f", type.max),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }
    }
}


sealed class StatsRowType{
    object Titles: StatsRowType()

    data class Speed(
        val min: Double,
        val med: Double,
        val max: Double
    ) : StatsRowType()

    data class Energy(
        val min: Double,
        val med: Double,
        val max: Double
    ) : StatsRowType()
}