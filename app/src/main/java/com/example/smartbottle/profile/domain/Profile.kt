package com.example.smartbottle.profile.domain

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val age: Int? = null,
    val height: Double? = null,
    val sex: String? = null,
    val user_id: String? = null,
    val weight: Double? = null,
    val totalDays: Int? = null,
    val longestStreak: Int? = null,
    val hydration: Int? = null,

    // New structured fields
    val alertTemperature: String = "",   // e.g., 24.0
    val hydrationReminder: String = "",     // minutes → 150
    val dndStart: String = "",              // e.g., 1300
    val dndEnd: String = ""                 // e.g., 2100
)

