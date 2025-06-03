package com.example.smartbottle.water.presentation

import com.example.smartbottle.water.domain.DailyHydration
import com.example.smartbottle.water.domain.Environment
import androidx.compose.ui.graphics.Color


data class HomeState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val dailyHydration: DailyHydration? = null,
    val environment: Environment? = null,
    val reminders: List<Reminder> = emptyList(),
    val dirtyWater: Boolean = false
)

data class Reminder(
    val title: String,
    val subtitle: String,
    val color: Color
)
