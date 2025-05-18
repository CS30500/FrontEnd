package com.example.smartbottle.history.presentation

import com.example.smartbottle.history.domain.HistoryItem
import java.time.YearMonth

data class HistoryState(
    val isLoading: Boolean = false,
    val historyList: List<HistoryItem> = emptyList(),
    val monthPercents: List<Pair<Int, Float>> = emptyList(),
    val monthStatistics: List<Float> = emptyList(),
    val selectedDay: Int = 1,
    val selectedMonth: YearMonth = YearMonth.now(),
    val streakCount: Int = 0,
    val isError: Boolean = false
)

