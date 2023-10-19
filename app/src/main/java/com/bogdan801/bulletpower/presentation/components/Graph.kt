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
            val util = GraphDataUtil(data)

            val xEndPadding = if(data.size>16) 2 else 0
            val xStepSize  = (maxWidth - 48.dp) / (data.size + 2 + xEndPadding)
            val xAxis = AxisData.Builder()
                .steps(data.size)
                .labelData {
                    if(data.size > 16){
                        if(it%2==0) it.toString() else ""
                    }
                    else it.toString()
                }
                .axisStepSize(xStepSize)
                .backgroundColor(backgroundColor)
                .labelAndAxisLinePadding(8.dp)
                .axisLineColor(axisColor)
                .axisLabelColor(labelsColor)
                .axisLabelFontSize(MaterialTheme.typography.labelMedium.fontSize)
                .shouldDrawAxisLineTillEnd(true)
                .build()

            val yAxis = AxisData.Builder()
                .steps(util.steps)
                .labelData { util.label(it) }
                .axisStepSize(100.dp)
                .backgroundColor(backgroundColor)
                .labelAndAxisLinePadding(16.dp)
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
                                    y = util.yLowerBound
                                ),
                                Point(
                                    x = (data.size + xEndPadding + 1).toFloat(),
                                    y = util.yUpperBound
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
                    text = "Мінімум - ${String.format("%.2f", util.minY)}\n" +
                            "Середнє - ${String.format("%.2f", util.medY)}\n" +
                            "Максимум - ${String.format("%.2f", util.maxY)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


private class GraphDataUtil(
    data: List<Point>,
    enableAxisPadding: Boolean = true
){
    val minY = data.minOf { it.y }
    val medY = data.sumOf { it.y.toDouble() } / data.size.toFloat()
    val maxY = data.maxOf { it.y }

    private val difference = maxY - minY
    private val yPadding = if(enableAxisPadding) (difference * 0.1).roundToInt() else 0

    val yLowerBound = setLowerBound(floor(minY).toInt(), ceil(maxY).toInt(), yPadding)
    val yUpperBound = setUpperBound(floor(minY).toInt(), ceil(maxY).toInt(), yPadding)

    private val yIntRange = yUpperBound.toInt() - yLowerBound.toInt()

    private val scaleFactor = setScaleFactor(yIntRange)

    val steps = (yIntRange / scaleFactor).roundToInt()

    fun label(i: Int) = String.format("%.1f", yLowerBound + (scaleFactor * i)).replace(".0", "")

    private fun setScaleFactor(range: Int) = when {
        range > 5000 -> 500.0
        range >= 2500 -> 200.0
        range >= 1000 -> 100.0
        range >= 500 -> 50.0
        range >= 250 -> 20.0
        range >= 100 -> 10.0
        range >= 50 -> 5.0
        range >= 25 -> 2.0
        range >= 10 -> 1.0
        range >= 5 -> 0.5
        range >= 2 -> 0.2
        range < 2 -> 0.1
        else -> 100.0
    }

    private fun setLowerBound(min: Int, max: Int, padding: Int): Float {
        val minP = min - padding
        val maxP = max + padding
        val range = maxP - minP
        val factor = setScaleFactor(range)
        return if(range != 0) (minP - (minP % factor).roundToInt()).toFloat() else minP - 0.5f
    }

    private fun setUpperBound(min: Int, max: Int, padding: Int): Float {
        val minP = min - padding
        val maxP = max + padding
        val range = maxP - minP
        val factor = setScaleFactor(range)
        return if(range != 0) ((maxP.toDouble() + factor - (maxP % factor)).roundToInt()).toFloat() else maxP + 0.5f
    }
}