package com.example.my_login



import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DonutChartData(
    val value: Float,
    val color: Color
)

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    chartData: List<DonutChartData>,
    strokeWidth: Dp = 30.dp,
    gap: Float = 5f
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(chartData) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val total = chartData.sumOf { it.value.toDouble() }.toFloat()
            if (total == 0f) return@Canvas

            var startAngle = -90f
            chartData.forEach {
                val sweep = (it.value / total) * 360f
                drawDonut(
                    color = it.color,
                    startAngle = startAngle,
                    sweepAngle = sweep * animatedProgress.value,
                    strokeWidth = strokeWidth,
                    gap = gap
                )
                startAngle += sweep
            }
        }
    }
}

private fun DrawScope.drawDonut(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    strokeWidth: Dp,
    gap: Float
) {
    if (sweepAngle == 0f) return

    val stroke = Stroke(width = strokeWidth.toPx())
    val diameter = size.minDimension - stroke.width
    val topLeft = Offset(
        (size.width - diameter) / 2f,
        (size.height - diameter) / 2f
    )
    drawArc(
        color = color,
        startAngle = startAngle + gap / 2,
        sweepAngle = sweepAngle - gap,
        useCenter = false,
        topLeft = topLeft,
        size = Size(diameter, diameter),
        style = stroke
    )
}