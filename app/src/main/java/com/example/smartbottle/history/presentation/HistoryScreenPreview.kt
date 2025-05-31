package com.example.smartbottle.history.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartbottle.history.domain.HistoryItem
import java.time.YearMonth

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    val mockState = HistoryState(
        isLoading = false,
        isError = false,
        streakCount = 5,
        historyList = listOf(
            HistoryItem("2023-10-01", 2000.0, 1500.0),
            HistoryItem("2023-10-02", 2500.0, 2500.0),
            HistoryItem("2023-10-03", 2000.0, 1800.0)
        ),
        monthPercents = listOf(
            1 to 0.75f,
            2 to 1.0f,
            3 to 0.9f
        ),
        monthStatistics = listOf(0.7f, 0.85f, 0.9f, 0.2f),
        selectedDay = 1,
        selectedMonth = YearMonth.now()
    )

    HistoryScreenCore(
        state = mockState,
        onAction = {},
        onNavigation = {}
    )
}
