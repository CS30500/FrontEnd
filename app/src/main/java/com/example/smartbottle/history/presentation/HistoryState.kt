package com.example.smartbottle.history.presentation

data class HistoryState(
    val isLoading: Boolean = false,
    val historyList: List<HistoryItem> = emptyList(),
    val isError: Boolean = false
)

data class HistoryItem(
    val date: String,
    val totalIntakeMl: Float,
    val targetMl: Float
)