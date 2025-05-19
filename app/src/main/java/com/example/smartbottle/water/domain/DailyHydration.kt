package com.example.smartbottle.water.domain

import kotlinx.serialization.Serializable

@Serializable
data class DailyHydration(
    val date: String = "",
    val target_ml: Double = 0.0,
    val total_intake_ml: Double = 0.0
)