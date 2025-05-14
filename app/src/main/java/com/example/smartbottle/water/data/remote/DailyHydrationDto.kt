package com.example.smartbottle.water.data.remote

data class DailyHydrationDto(
    val date: String,
    val target_ml: Double,
    val total_intake_ml: Int
)