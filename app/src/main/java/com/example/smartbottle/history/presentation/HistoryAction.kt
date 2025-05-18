package com.example.smartbottle.history.presentation

import com.example.smartbottle.history.domain.HistoryItem
import com.example.smartbottle.history.domain.HistoryList
import java.time.YearMonth

sealed interface HistoryAction {
    data class ChangeMonth(val newMonth: YearMonth) : HistoryAction
    data class ChangeDay(val newDay: Int) : HistoryAction
    data class GetMonthData(val month: YearMonth, val historyList: List<HistoryItem>) : HistoryAction
}