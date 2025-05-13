package com.example.smartbottle.history.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItemDto(
    val date: String,
    val target_ml: Double?,
    val total_intake_ml: Double?
)