package com.example.smartbottle.history.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class HistoryListDto(
    val history: List<HistoryItemDto>?
)