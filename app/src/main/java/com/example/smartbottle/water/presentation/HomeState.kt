package com.example.smartbottle.water.presentation

import com.example.smartbottle.water.domain.DailyHydration

data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val dailyHydration: DailyHydration? = null,
    val outsideTemperature: Double? = null,
    val waterTemperature: Double? = null,
    val humidity: Double? = null,
)