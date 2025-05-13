package com.example.smartbottle.history.domain

import kotlinx.serialization.Serializable

@Serializable
data class HistoryList(
    val history: List<HistoryItem>?
)