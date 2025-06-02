package com.example.smartbottle.profile.presentation

import com.example.smartbottle.auth.presentation.signup.RegisterAction

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

    data class ChangeTemp(val newTemp: String) : ProfileAction
    data class ChangeReminder(val newReminder: String) : ProfileAction
    data class ChangeDndStart(val newDndStart: String) : ProfileAction
    data class ChangeDndEnd( val newDndEnd: String) : ProfileAction


}