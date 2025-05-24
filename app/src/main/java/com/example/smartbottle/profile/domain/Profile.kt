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
    val hydration: Int? = null
)