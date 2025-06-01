//NotificationCard.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.runtime.*
import com.example.smartbottle.profile.domain.Profile

@Composable
fun NotificationCard(profile: Profile?) {
    var isEditing by remember { mutableStateOf(false) }

    EditableCardLayout(
        title = "Notification Settings",
        isEditing = isEditing,
        onEditToggle = { isEditing = !isEditing },
        onSave = { isEditing = false },
        readOnlyContent = {
            NotificationSettingRow(
                "High temperature alert",
                profile?.alertTemperature?.let { "over ${it.toInt()}°C" } ?: "--"
            )
            NotificationSettingRow(
                "Reminder after last hydration",
                profile?.hydrationReminder?.let { "${it / 60}h ${it % 60}m" } ?: "--"
            )
            NotificationSettingRow(
                "Do not disturb",
                if (profile?.dndStart != null && profile.dndEnd != null)
                    "${formatTime(profile.dndStart)} ~ ${formatTime(profile.dndEnd)}"
                else "--"
            )
        },
        editableContent = {
            NotificationSettingRow("High temperature alert", "Edit mode")
            NotificationSettingRow("Reminder after last hydration", "Edit mode")
            NotificationSettingRow("Do not disturb", "Edit mode")
        }
    )
}

fun formatTime(military: Int): String {
    val hour = military / 100
    val minute = military % 100
    return "%d:%02d".format(hour, minute)
}