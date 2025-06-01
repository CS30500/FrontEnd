package com.example.smartbottle.profile.presentation

import com.example.smartbottle.profile.domain.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class SaveNotificationSettings(
    val alertTemperature: Double?,
    val hydrationReminder: Int?,
    val dndStart: Int?,
    val dndEnd: Int?
)