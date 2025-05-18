package com.example.smartbottle.history.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.history.domain.HistoryItem
import com.example.smartbottle.history.domain.HistoryList
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.domain.HistoryResult
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    var state by mutableStateOf(HistoryState())
        private set

    init{
        loadHistory()
        getMonthData(state.selectedMonth, state.historyList)
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.ChangeMonth -> {
                state = state.copy(selectedMonth = action.newMonth)
                getMonthData(state.selectedMonth, state.historyList)
            }
            is HistoryAction.ChangeDay -> {
                state = state.copy(selectedDay = action.newDay)
            }
            is HistoryAction.GetMonthData -> {
                getMonthData(action.month, action.historyList)
            }

        }
    }


    private fun getMonthData(month: YearMonth, historyList: List<HistoryItem>) {
        // 1) 해당 월(YearMonth)의 데이터만 필터링
        val monthItems = historyList.filter {
            val localDate = LocalDate.parse(it.date)
            localDate.year == month.year && localDate.month == month.month
        }

        // 2) 이번 달 날짜(1~말일) 순회하며 total_intake_ml / target_ml 퍼센트 계산
        val daysInMonth = 1..month.lengthOfMonth()

        val dayProgressList = daysInMonth.map { day ->
            // 단일 날짜 데이터 찾아서 totalIntake/targetIntake 퍼센트 계산
            val historyItem = monthItems.find {
                val localDate = LocalDate.parse(it.date)
                localDate.dayOfMonth == day
            }

            val totalIntake = historyItem?.total_intake_ml ?: 0.0
            val targetIntake = historyItem?.target_ml ?: 0.0
            val ratio = if (targetIntake > 0.0) {
                (totalIntake / targetIntake).toFloat()
            } else {
                0f
            }

            // day와 ratio를 Pair로 묶어 반환
            day to ratio
        }

        // 전체 날짜 수
        val totalDays = dayProgressList.size.takeIf { it > 0 } ?: 1

        // 3) 비율별 개수를 구한 뒤, 전체 대비 비율(0~1)을 계산
        val count100 = dayProgressList.count { (_, ratio) -> ratio >= 1f }
        val count80to100 = dayProgressList.count { (_, ratio) -> ratio >= 0.8f && ratio < 1f }
        val count60to80 = dayProgressList.count { (_, ratio) -> ratio >= 0.6f && ratio < 0.8f }
        val countUnder60 = dayProgressList.count { (_, ratio) -> ratio < 0.6f }

        val ratio100 = count100.toFloat() / totalDays
        val ratio80to100 = count80to100.toFloat() / totalDays
        val ratio60to80 = count60to80.toFloat() / totalDays
        val ratioUnder60 = countUnder60.toFloat() / totalDays

        // 4) 상태에 반영
        state = state.copy(
            monthPercents = dayProgressList,  // (day, ratio) 리스트
            monthStatistics = listOf(
                ratio100,
                ratio80to100,
                ratio60to80,
                ratioUnder60
            )
        )

    }



    private fun loadHistory(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            historyRepository.getHistory().collect{ result ->
                when(result){
                    is HistoryResult.Error -> {
                        state = state.copy(
                            isError = true
                        )

                    }
                    is HistoryResult.Success -> {
                        state = state.copy(
                            isError = false,
                            historyList = result.data?.history ?: emptyList(),
                            streakCount = getConsecutiveFullDaysUntilYesterday(result.data?.history ?: emptyList())
                            )
                    }
                }
            }

            getMonthData(state.selectedMonth, state.historyList)

            state = state.copy(
                isLoading = false
            )
        }
    }
}

private fun getConsecutiveFullDaysUntilYesterday(historyList: List<HistoryItem>): Int {
    val sortedList = historyList.sortedByDescending { LocalDate.parse(it.date) }
    val yesterday = LocalDate.now().minusDays(1)
    var consecutiveCount = 0
    var currentDay = yesterday

    // 어제부터 시작해 하루씩 되돌아가며 검사
    while (true) {
        val dayRecord = sortedList.firstOrNull {
            val recordDate = LocalDate.parse(it.date)
            recordDate == currentDay
        } ?: break

        // 목표가 0보다 클 때, 100% 달성 여부 판단
        if (dayRecord.target_ml!! > 0 && (dayRecord.total_intake_ml?.div(dayRecord.target_ml))!! >= 1.0) {
            consecutiveCount++
            currentDay = currentDay.minusDays(1)
        } else {
            break
        }
    }

    return consecutiveCount
}
