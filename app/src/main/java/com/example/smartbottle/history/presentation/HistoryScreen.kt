package com.example.smartbottle.history.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartbottle.core.presentation.ui.theme.SmartBottleTheme
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HistoryScreen(
    viewmodel : HistoryViewModel = koinViewModel(),
    onNavigation : () -> Unit
){
    HistoryScreenCore(
        state = viewmodel.state,
        onAction = viewmodel::onAction,
        onNavigation
    )
}

@Composable
private fun HistoryScreenCore(
    state : HistoryState,
    onAction : (HistoryAction) -> Unit,
    onNavigation : () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val firstItem = state.historyList.firstOrNull()

        Column(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(style = MaterialTheme.typography.titleLarge.toSpanStyle()) {
                        append("You are on a")
                    }
                    withStyle(style = MaterialTheme.typography.displayLarge.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.primary
                    )) {
                        append(" 16 Day\n")
                    }
                    withStyle(style = MaterialTheme.typography.titleLarge.toSpanStyle()) {
                        append(" hydration Streak")
                    }
                },
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                "Monthly Records",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding( top = 16.dp)
            )
        }

        val sampleDays = (1..31).map { it to (0.1f * (it % 10)) }
        CalendarWithProgress(selectedDay = 24)

        Column(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Monthly Statistics"
                , style = MaterialTheme.typography.titleMedium)
        }

        MonthlyStatisticsCard()

        if (firstItem == null) {
            Text("데이터가 없습니다.")
        } else {
            // 정상적으로 가져온 firstItem에 대한 UI 처리
            Text("시간: ${firstItem.date}")
        }
    }
}
@Composable
fun CalendarWithProgress(
    modifier: Modifier = Modifier,
    selectedDay: Int? = null
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val firstDayOfWeek = currentMonth.atDay(1).dayOfWeek.value % 7 // Sunday=0
    val daysInMonth = currentMonth.lengthOfMonth()
    val days = (1..daysInMonth).map { it to (0.1f * (it % 10)) }

    val monthName = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).uppercase()
    val year = currentMonth.year

    val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    Column(modifier = modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)) {

        // Header with arrows
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                currentMonth = currentMonth.minusMonths(1)
            }) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
            }

            Text("$monthName $year", style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = {
                currentMonth = currentMonth.plusMonths(1)
            }) {
                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 요일 헤더
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach {
                Text(it, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 날짜 그리드
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth().height(240.dp)
        ) {
            items(daysInMonth + firstDayOfWeek) { index ->
                if (index < firstDayOfWeek) {
                    Box(modifier = Modifier.size(40.dp))
                } else {
                    val day = days[index - firstDayOfWeek]
                    DayWithProgress(day.first, day.second, isSelected = selectedDay == day.first)
                }
            }
        }
    }
}

@Composable
fun DayWithProgress(day: Int, progress: Float, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(36.dp)) {
            val stroke = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)

            drawCircle(
                color = Color.LightGray,
                style = stroke
            )
            drawArc(
                color = Color(0xFF3B82F6), // blue
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = stroke
            )
        }

        Text(
            text = day.toString(),
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            ),
            modifier = if (isSelected) Modifier
                .background(Color(0xFF3B82F6), shape = CircleShape)
                .padding(6.dp) else Modifier
        )
    }
}

@Composable
fun MonthlyStatisticsCard(percentList: List<Float> = listOf(0.1f, 0.2f, 0.3f, 0.4f)) {
    val levels = listOf(
        1.0f to "100%",
        0.8f to "80%",
        0.6f to "60%",
        0.4f to "LESS"
    )
    val colors = listOf(
        Color(0xFF3B82F6), // 100%
        Color(0xFF60A5FA), // 80%
        Color(0xFF93C5FD), // 60%
        Color(0xFFBFDBFE)  // LESS
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 막대 progress bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE5E7EB))
        ) {
            colors.forEachIndexed() { index, color ->
                Box(
                    modifier = Modifier
                        .weight(percentList[index])
                        .fillMaxHeight()
                        .background(colors[index])
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 범례 (circle + label)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            levels.forEachIndexed { index, (_, label) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(colors[index], shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.Gray,
                            fontWeight = if (label == "100%") FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview(){
    SmartBottleTheme {
        HistoryScreenCore(
            state = HistoryState(),
            onAction = {},
            onNavigation = {}
        )
    }
}