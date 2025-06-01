//NotificationCard.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.runtime.*
import com.example.smartbottle.profile.domain.Profile
import androidx.compose.runtime.Composable
import com.example.smartbottle.profile.presentation.ProfileViewModel
import com.example.smartbottle.profile.presentation.ProfileAction




@Composable
fun NotificationCard(
    profile: Profile?,
    viewModel: ProfileViewModel
) {
    var isEditing by remember { mutableStateOf(false) }

    // Editable states
    var temp by remember { mutableStateOf(profile?.alertTemperature?.toString() ?: "") }
    var reminder by remember { mutableStateOf(profile?.hydrationReminder?.toString() ?: "") }
    var dndStart by remember { mutableStateOf(profile?.dndStart?.toString() ?: "") }
    var dndEnd by remember { mutableStateOf(profile?.dndEnd?.toString() ?: "") }


    EditableCardLayout(
        title = "Notification Settings",
        isEditing = isEditing,
        onEditToggle = { isEditing = !isEditing },
        onSave = {
            val newProfile = profile?.copy(
                alertTemperature = temp.toDoubleOrNull(),
                hydrationReminder = reminder.toIntOrNull(),
                dndStart = dndStart.toIntOrNull(),
                dndEnd = dndEnd.toIntOrNull()
            )

            if (newProfile != null) {
                viewModel.onAction(
                    ProfileAction.SaveNotificationSettings(
                        alertTemperature = newProfile.alertTemperature,
                        hydrationReminder = newProfile.hydrationReminder,
                        dndStart = newProfile.dndStart,
                        dndEnd = newProfile.dndEnd
                    )
                )
            }


            isEditing = false
        }
        ,
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
            NotificationInputRow(
                label = "High temperature alert",
                value = temp,
                onValueChange = { temp = it }
            )
            NotificationInputRow(
                label = "Reminder after last hydration",
                value = reminder,
                onValueChange = { reminder = it }
            )
            NotificationInputRow(
                label = "Do not disturb",
                value = "$dndStart ~ $dndEnd", // optional: later split into two inputs
                onValueChange = { /* no-op for now */ }
            )
        }

    )
}



fun formatTime(military: Int): String {
    val hour = military / 100
    val minute = military % 100
    return "%d:%02d".format(hour, minute)
}