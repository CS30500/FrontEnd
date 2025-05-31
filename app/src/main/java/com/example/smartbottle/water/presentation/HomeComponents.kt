// HomeComponents.kt

package com.example.smartbottle.water.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbottle.RunningService

@Composable
fun DailyProgress(state: HomeState) {
    val intakeMl = state.dailyHydration?.total_intake_ml?.toFloat() ?: 0f
    val targetMl = (state.dailyHydration?.target_ml?.toFloat() ?: 1f).coerceAtLeast(1f)
    val progress = (intakeMl / targetMl).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()               // take up available width
            .aspectRatio(1f)              // force height == width
            .padding(32.dp),              // optional padding
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(width = 32.dp.toPx(), cap = StrokeCap.Round)
            // Draw full circle background
            drawArc(
                color = Color.LightGray,
                startAngle = 0f, sweepAngle = 360f, useCenter = false,
                style = stroke
            )
            // Draw progress arc
            drawArc(
                color = Color(0xFF3B82F6),
                startAngle = -90f, sweepAngle = 360f * progress, useCenter = false,
                style = stroke
            )
        }

        when {
            state.isError   -> Text("Error")
            state.isLoading -> Text("Loading")
            else            -> {
                val intakeL = intakeMl / 1000f
                Text(
                    text = String.format("%.1f L", intakeL),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun MetricCircle(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF939393))
        Spacer(Modifier.height(8.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                Canvas(Modifier.size(72.dp)) {
                    drawArc(
                        color = color.copy(alpha = 0.2f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            }

            Spacer(Modifier.height(6.dp))

            Box(
                Modifier
                    .size(6.dp)
                    .background(color, CircleShape)
            )
        }
    }
}

@Composable
fun ReminderList(reminders: List<Reminder>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        reminders.forEach { rem ->
            ReminderItem(rem)
        }
    }
}

@Composable
fun ReminderItem(reminder: Reminder) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .offset(y = 4.dp)
                .size(6.dp)
                .background(reminder.color, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(reminder.title, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(Modifier.height(4.dp))
            Text(reminder.subtitle, fontSize = 12.sp, color = Color(0xFF666666))
        }
    }
}

@Composable
fun StartStopButtons(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Button(
            onClick = {
                Intent(context, RunningService::class.java).apply {
                    action = RunningService.Actions.START.toString()
                    context.startService(this)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF457EDF), shape = CircleShape),
        ) {
            Text("Start", color = Color.White)
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                Intent(context, RunningService::class.java).apply {
                    action = RunningService.Actions.STOP.toString()
                    context.startService(this)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF457EDF), shape = CircleShape),
        ) {
            Text("Stop", color = Color.White)
        }
    }
}
