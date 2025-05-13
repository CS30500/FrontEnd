package com.example.smartbottle.history.domain

import kotlinx.serialization.Serializable

@Serializable
data class HistoryItem(
    val date: String,
    val target_ml: Double?,
    val total_intake_ml: Double?
)