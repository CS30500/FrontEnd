// File: DailyHydrationDto.kt
package com.example.smartbottle.water.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class DailyHydrationDto(
    val date: String,
    val target_ml: Double,
    val total_intake_ml: Double,
    val outsideTemperature: Double? = null,
    val waterTemperature: Double? = null,
    val humidity: Double? = null
)

