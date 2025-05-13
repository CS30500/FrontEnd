package com.example.smartbottle.history.presentation

import com.example.smartbottle.history.domain.HistoryItem

data class HistoryState(
    val isLoading: Boolean = false,
    val historyList: List<HistoryItem> = emptyList(),
    val isError: Boolean = false
)

