package com.example.smartbottle.history.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItemDto(
    val date: String,
    val total_intake_ml: Double?,
    val target_ml: Double?,

)