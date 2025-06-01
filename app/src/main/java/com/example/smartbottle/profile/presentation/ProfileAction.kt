package com.example.smartbottle.profile.presentation

sealed interface ProfileAction {
    data class SaveNotificationSettings(
        val alertTemperature: Double?,
        val hydrationReminder: Int?,
        val dndStart: Int?,
        val dndEnd: Int?
    ) : ProfileAction

    // (optional) Add this if you plan to save personal info as well:
    data class SavePersonalInfo(
        val age: Int?,
        val sex: String?,
        val weight: Double?,
        val height: Double?
    ) : ProfileAction
}