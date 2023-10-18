package com.bogdan801.bulletpower.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    data: List<Point>,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    gridColor: Color = MaterialTheme.colorScheme.outlineVariant,
    axisColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelsColor: Color = MaterialTheme.colorScheme.onSurface,
    lineColor: Color = MaterialTheme.colorScheme.primary,
    xTitle: String = "",
    yTitle: String = "",
    showStatistics: Boolean = true
) {
    if(data.isNotEmpty()){
        BoxWithConstraints(
            modifier = modifier
                .background(backgroundColor)
                .padding(horizontal = 8.dp)
        ) {
            val width = maxWidth
            val minY = data.minOf { it.y }
            val medY = data.sumOf { it.y.toDouble() }/data.size.toFloat()
            val maxY = data.maxOf { it.y }

            val isYPrecise = maxY - minY < 4
            val isYNotPrecise = maxY - minY > 16
            val precisionFactor = when {
                maxY - minY >= 16 -> 0.5f
                maxY - minY >= 4 -> 1f
                maxY - minY >= 2 -> 2f
                maxY - minY < 2 -> 4f
                else -> 1f
            }

            val yTopPadding = if(isYPrecise) {
                if(maxY - minY == 0f) 1 else 0
            } else 2

            val yBottomPadding = if(isYPrecise) {
                if (maxY - minY == 0f) 1 else 0
            } else 1
            val xEndPadding = if(data.size>16) 2 else 0

            val stepSize  = (width - 48.dp) / (data.size + 1 + xEndPadding)
            val xAxis = AxisData.Builder()
                .steps(data.size)
                .labelData {
                    if(data.size > 16){
                        if(it%2==0) it.toString() else ""
                    }
                    else it.toString()
                }
                .axisStepSize(stepSize)
                .backgroundColor(backgroundColor)
                .labelAndAxisLinePadding(8.dp)
                .axisLineColor(axisColor)
                .axisLabelColor(labelsColor)
                .axisLabelFontSize(MaterialTheme.typography.labelMedium.fontSize)
                .shouldDrawAxisLineTillEnd(true)
                .build()

            val minYAxisValue = floor((minY - yBottomPadding)).toInt()
            val maxYAxisValue = ceil((maxY + yTopPadding)).toInt()
            val ySteps = if(isYPrecise || isYNotPrecise) ((maxYAxisValue - minYAxisValue) * precisionFactor).roundToInt()
                         else maxYAxisValue - minYAxisValue
            val yAxis = AxisData.Builder()
                .steps(ySteps)
                .labelData {
                    if(isYPrecise){
                        (minYAxisValue + (it/precisionFactor)).toString()
                    }
                    else if(isYNotPrecise) (minYAxisValue + (it/precisionFactor)).toInt().toString()
                    else {
                        if(ySteps > 16){
                            if(it%2==0) (minYAxisValue + it).toString() else ""
                        }
                        else (minYAxisValue + it).toString()
                    }
                }
                .axisStepSize(100.dp)
                .backgroundColor(backgroundColor)
                .labelAndAxisLinePadding(if(isYPrecise) 32.dp else 12.dp)
                .axisLineColor(axisColor)
                .axisLabelColor(labelsColor)
                .axisLabelFontSize(MaterialTheme.typography.labelMedium.fontSize)
                .build()

            val lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    plotType = PlotType.Line,
                    lines = listOf(
                        Line(
                            dataPoints = listOf(
                                Point(
                                    x = 0f,
                                    y = minYAxisValue.toFloat()
                                ),
                                Point(
                                    x = (data.size + xEndPadding).toFloat(),
                                    y = maxYAxisValue.toFloat()
                                )
                            ),
                            lineStyle = LineStyle(color = Color.Transparent),
                            intersectionPoint = null,
                            selectionHighlightPoint = null,
                            shadowUnderLine = null,
                            selectionHighlightPopUp = null
                        ),
                        Line(
                            dataPoints = data,
                            lineStyle = LineStyle(
                                lineType = LineType.Straight(isDotted = false),
                                color = lineColor,
                                width = 9f
                            ),
                            intersectionPoint = IntersectionPoint(
                                color = lineColor,
                                radius = 5.dp
                            ),
                            selectionHighlightPoint = SelectionHighlightPoint(
                                color = lineColor,
                                radius = 7.dp
                            ),
                            shadowUnderLine = ShadowUnderLine(
                                alpha = 0.4f,
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        lineColor,
                                        Color.Transparent
                                    )
                                )
                            ),
                            selectionHighlightPopUp = SelectionHighlightPopUp()
                        )
                    )
                ),
                xAxisData = xAxis,
                yAxisData = yAxis,
                isZoomAllowed = false,
                backgroundColor = backgroundColor,
                gridLines = GridLines(
                    color = gridColor,
                    lineWidth = 1.dp
                ),
                paddingRight = 0.dp
            )

            LineChart(
                modifier = Modifier
                    .fillMaxSize(),
                lineChartData = lineChartData
            )

            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = 40.dp, y = 8.dp),
                text = yTitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-38).dp),
                text = xTitle,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if(showStatistics){
                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(y = 8.dp),
                    text = "Мінімум - ${String.format("%.2f", minY)}\n" +
                            "Середнє - ${String.format("%.2f", medY)}\n" +
                            "Максимум - ${String.format("%.2f", maxY)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}