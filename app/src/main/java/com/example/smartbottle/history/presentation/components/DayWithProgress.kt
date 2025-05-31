package com.example.smartbottle.history.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.smartbottle.core.presentation.ui.theme.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.smartbottle.history.presentation.HistoryAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DayWithProgress(day: Int, progress: Float, isSelected: Boolean, onAction: (HistoryAction) -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .aspectRatio(1f)
            .clickable { onAction(HistoryAction.ChangeDay(day)) },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stroke = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)

                drawCircle(
                    color = Color(0xFFE4E4E4),
                    style = stroke
                )
                drawArc(
                    color = Blue3,
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    style = stroke
                )
            }

            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                    color = if (isSelected) Blue4 else Gray2,
                    fontSize = 12.sp
                ),
                modifier = if (isSelected) Modifier.offset(y = (-0.7).dp) else Modifier
            )
        }
    }
}
