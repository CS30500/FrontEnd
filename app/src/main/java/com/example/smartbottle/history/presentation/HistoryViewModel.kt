package com.example.smartbottle.history.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.history.domain.HistoryItem
import com.example.smartbottle.history.domain.HistoryList
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.domain.HistoryResult
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.YearMonth

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val tag = "HistoryViewModel: "

    var state by mutableStateOf(HistoryState())
        private set

    init {
        loadHistory(state.selectedMonth)
    }

    fun onAction(action: HistoryAction) =
        when (action) {
        is HistoryAction.ChangeMonth -> {
            state = state.copy(selectedMonth = action.newMonth)
            loadHistory(action.newMonth)
        }
        is HistoryAction.ChangeDay   -> {
            state = state.copy(selectedDay = action.newDay)
        }

        is HistoryAction.GetMonthData -> TODO()
    }

    /** ─────────── 월-별 기록 로드 & 상태 반영 ─────────── **/
    private fun loadHistory(month: YearMonth) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, isError = false)

            try {
                /** ① UI에 보여줄 달 */
                val uiList = fetchHistory(month)

                Log.d(tag, "loadHistory: $uiList")

                /** ② streak 계산용 달 (어제가 속한 달) */
                val currentMonth = YearMonth.from(LocalDate.now().minusDays(1))
                val streakList  =
                    if (currentMonth == month) uiList else fetchHistory(currentMonth)

                /* 통계 계산 */
                val (percents, stats) = calculateMonthData(month, uiList)

                state = state.copy(
                    isLoading       = false,
                    historyList     = uiList,
                    monthPercents   = percents,
                    monthStatistics = stats,
                    streakCount     = getConsecutiveFullDaysUntilYesterday(streakList)
                )

            } catch (e: Exception) {    // 네트워크 오류·파싱 오류 등
                state = state.copy(isLoading = false, isError = true)
            }
        }
    }

    /** ────────────── 월 데이터 한 번만 받아오기 ────────────── **/
    private suspend fun fetchHistory(month: YearMonth): List<HistoryItem> =
        when (val result = historyRepository
            .getHistory(month.year, month.monthValue)
            .first()) {

            is HistoryResult.Success -> result.data?.history ?: emptyList()
            is HistoryResult.Error   -> throw IOException("History load failed")
        }

    /** ──────────── 월 통계 계산 (state 미참조) ──────────── **/
    private fun calculateMonthData(
        month: YearMonth,
        historyList: List<HistoryItem>
    ): Pair<List<Pair<Int, Float>>, List<Float>> {

        val monthItems = historyList.filter {
            val d = LocalDate.parse(it.date)
            d.year == month.year && d.month == month.month
        }

        val progress = (1..month.lengthOfMonth()).map { day ->
            val item   = monthItems.find { LocalDate.parse(it.date).dayOfMonth == day }
            val total  = item?.total_intake_ml ?: 0.0
            val target = item?.target_ml ?: 0.0
            val ratio  = if (target > 0.0) (total / target).toFloat() else 0f
            day to ratio
        }

        val days = progress.size.takeIf { it > 0 } ?: 1
        val stats = listOf(
            progress.count { it.second >= 1f }         .toFloat() / days,
            progress.count { it.second in 0.8f..<1f }  .toFloat() / days,
            progress.count { it.second in 0.6f..<0.8f }.toFloat() / days,
            progress.count { it.second < 0.6f }        .toFloat() / days
        )
        return progress to stats
    }
}

/** ─────────── streak 계산 (어제부터 연속 달성) ─────────── **/
private fun getConsecutiveFullDaysUntilYesterday(list: List<HistoryItem>): Int {
    val sorted  = list.sortedByDescending { LocalDate.parse(it.date) }
    var current = LocalDate.now().minusDays(1)
    var streak  = 0

    while (true) {
        val rec = sorted.firstOrNull { LocalDate.parse(it.date) == current } ?: break
        val tgt = rec.target_ml ?: 0.0
        val ttl = rec.total_intake_ml ?: 0.0
        if (tgt > 0.0 && ttl / tgt >= 1.0) {
            streak++; current = current.minusDays(1)
        } else break
    }
    return streak
}
