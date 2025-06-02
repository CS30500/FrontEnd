//NotificationCard.kt
package com.example.smartbottle.profile.presentation.components

import androidx.compose.runtime.*
import com.example.smartbottle.profile.domain.Profile
import androidx.compose.runtime.Composable
import com.example.smartbottle.profile.presentation.ProfileViewModel
import com.example.smartbottle.profile.presentation.ProfileAction
import com.example.smartbottle.profile.presentation.ProfileState


@Composable
fun NotificationCard(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }

    EditableCardLayout(
        title = "Notification Settings",
        isEditing = isEditing,
        onEditToggle = { isEditing = !isEditing },
        onSave = {

                onAction(ProfileAction.SaveNotificationSettings(
                    alertTemperature = state.profile?.alertTemperature?.toDoubleOrNull(),
                    hydrationReminder = state.profile?.hydrationReminder?.toIntOrNull(),
                    dndStart = state.profile?.dndStart?.toIntOrNull(),
                    dndEnd = state.profile?.dndEnd?.toIntOrNull()
                ))


            isEditing = false
        }
        ,
        readOnlyContent = {
            NotificationSettingRow(
                "High temperature alert",
                if(state.profile?.alertTemperature != "") {
                    "over ${state.profile?.alertTemperature}°C"
                } else {
                    "--"
                }
            )
            NotificationSettingRow(
                "Reminder after last hydration",
                if (state.profile?.hydrationReminder != "" && state.profile?.hydrationReminder != "null") {
                    state.profile?.hydrationReminder?.toIntOrNull()?.let { minutes ->
                        "${minutes.div(60)}h ${minutes.rem(60)}m"
                    } ?: "--"
                } else {
                    "--"
                }
            )
            NotificationSettingRow(
                "Do not disturb",
                if (state.profile?.dndStart != "" && state.profile?.dndStart != "null")
                    "${state.profile?.dndStart?.let { formatTime(it.toInt()) }} ~ ${state.profile?.dndEnd?.let {
                        formatTime(
                            it.toInt())
                    }}"
                else "--"
            )
        },
        editableContent = {
            state.profile?.alertTemperature?.let { it ->
                NotificationInputRow(
                    label = "High temperature alert",
                    value = it,
                    onValueChange = { onAction(ProfileAction.ChangeTemp(it)) }
                )
            }
            state.profile?.hydrationReminder?.let { it  ->
                NotificationInputRow(
                    label = "Reminder after last hydration",
                    value = it,
                    onValueChange = { onAction(ProfileAction.ChangeReminder(it)) }
                )
            }
            NotificationInputRow(
                label = "Do not disturb (start time)",
                value = "${state.profile?.dndStart}", // optional: later split into two inputs
                onValueChange = { onAction(ProfileAction.ChangeDndStart(it)) }
            )

            NotificationInputRow(
                label = "Do not disturb (End time)",
                value = "${state.profile?.dndEnd}", // optional: later split into two inputs
                onValueChange = { onAction(ProfileAction.ChangeDndEnd(it)) }
            )
        }

    )
}



fun formatTime(military: Int): String {
    val hour = military / 100
    val minute = military % 100
    return "%d:%02d".format(hour, minute)
}