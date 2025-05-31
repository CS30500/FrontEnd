package com.example.smartbottle.history.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import com.example.smartbottle.core.presentation.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbottle.history.presentation.HistoryAction
import com.example.smartbottle.history.presentation.HistoryState
import java.time.format.TextStyle
import java.util.*

@Composable
fun CalendarWithProgress(
    modifier: Modifier = Modifier,
    onAction: (HistoryAction) -> Unit,
    state: HistoryState
) {
    val firstDayOfWeek = state.selectedMonth.atDay(1).dayOfWeek.value % 7
    val daysInMonth = state.selectedMonth.lengthOfMonth()
    val monthName = state.selectedMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).uppercase()
    val year = state.selectedMonth.year
    val daysOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    val dayProgressList = remember(state.monthPercents, state.selectedMonth) {
        (1..daysInMonth).map { d ->
            val prog = state.monthPercents.firstOrNull { it.first == d }?.second ?: 0f
            d to prog
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onAction(HistoryAction.ChangeMonth(state.selectedMonth.minusMonths(1))) }) {
                Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
            }

            Text(
                text = "$monthName $year",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Blue4
                )
            )

            IconButton(onClick = { onAction(HistoryAction.ChangeMonth(state.selectedMonth.plusMonths(1))) }) {
                Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            daysOfWeek.forEach {
                Text(it,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Gray2
                    ),
                    textAlign = TextAlign.Center)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 0.dp, max = 320.dp)
        ) {
            items(daysInMonth + firstDayOfWeek) { index ->
                if (index < firstDayOfWeek) {
                    Box(modifier = Modifier.size(40.dp))
                } else {
                    val (day, prog) = dayProgressList[index - firstDayOfWeek]
                    DayWithProgress(day, prog, state.selectedDay == day, onAction)
                }
            }
        }
    }
}
